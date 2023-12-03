package services

import models.IP
import models.Router
import models.Package
import models.Page
import models.dtos.WebSimulator
import java.util.*

class RouterServices {

    private val dijkstraService: DijkstraService = DijkstraService()
    
    fun arePackagesInDestinyAndReadyToBuildPage(actualRouter: Router, queue: Queue<Package>): Boolean {
        var inDestiny = false
        var ready = false

        queue.forEach { pack ->
            if (pack.getDestinationIP().getRouterID() == actualRouter.getRouterId()) {
                inDestiny = true
            }
        }

        if (!inDestiny) {
            return false
        }

        // Obtener un mutableSet de los destinationIp de los paquetes de la cola
        val destinationIpSet = queue.map { it.getDestinationIP() }.toMutableSet()

        destinationIpSet.forEach { destinationIp ->
            val totalPackages = queue.filter { it.getDestinationIP() == destinationIp }.size
            queue.forEach{pack ->
                if (pack.getDestinationIP() == destinationIp && pack.getDestinationIP().getRouterID() == actualRouter.getRouterId() && totalPackages == pack.getTotalPackages()) {
                    ready = pack.getPackageId() == pack.getTotalPackages() // Si el paquete actual es el ultimo de la pagina
                }

            }
        }

        return ready
    }
    fun isPackageInDestiny(actualRouter: Router): Boolean {
        var inDestiny = false

        actualRouter.getOutputBuffer().forEach { (routerId, queue) ->
            val lastPackage = queue.peek()
            if (queue.isNotEmpty() && lastPackage.getDestinationIP().getRouterID() == routerId) {
                inDestiny = true
                return@forEach
            }
        }

        return inDestiny
    }

    fun isReadyToBuildPage(actualRouter: Router): Boolean {
        var ready = false

        actualRouter.getOutputBuffer()[actualRouter.getRouterId()]
            ?.let { queue ->
                if (queue.isNotEmpty()) {
                    var packConNumeroMasAlto: Package = queue.peek()

                    queue.forEach { pack ->
                        if (pack.getPackageId() > packConNumeroMasAlto.getPackageId()) {
                            packConNumeroMasAlto = pack
                        }
                    }

                    ready = packConNumeroMasAlto.getPackageId() == packConNumeroMasAlto.getTotalPackages()
                }
            }

        return ready
    }

    fun buildPage(queue: Queue<Package>): Page? {
        println("Rearmando pagina...")
        val somePackage = queue.peek()
        var pageName = ""

        while (queue.isNotEmpty()) {
            val pack = queue.poll()
            pageName += pack.getPackageContent()
        }

        return somePackage?.let {
            Page(
                it.getPageId(),
                pageName,
                somePackage.getTotalPackages(),
                somePackage.getDestinationIP(),
                somePackage.getNextIP()
            )
        }


    }

    //Este metodo tendria que recibir por pasaje por referencia:
    //      * la cola de paginas de un router
    //      * la pagina que queremos agregar
    fun receivePages(router: Router, page: Page){
        router.getReceivedPages().add(page)
    }

    fun pageSegmentation(router: Router, page: Page, bandWidth: Int){
        page.getPageContent().chunked(bandWidth).fold(1) { acc, substring ->
            val nextRouterIp = dijkstraService.nextVertexInShortestPath(router.getRouterId(), page.getDestinationRouterId())
            val pack = nextRouterIp?.let { createPackage(substring, page, acc, it, bandWidth) }
            if (pack != null) {
                queuePackages(router, pack)
            }
            acc + 1
        }
        router.getReceivedPages().poll()
    }

    private fun createPackage(substring: String, page: Page, id: Int, nextRouterId: Int, bandWidth: Int): Package {
        val nextIP = IP(nextRouterId, page.getDestinationIP().getTerminalId())
        val totalPackages = (page.getPageContent().length + bandWidth - 1) / bandWidth

        return Package(id, substring, page.getPageId(), totalPackages, page.getDestinationIP(),page.getOriginIP(), nextIP)
    }

    private fun queuePackages(router: Router, pack: Package){
        if(pack.getActualIP().getRouterID() != pack.getDestinationIP().getRouterID()){
            router.getOutputBuffer()[pack.getNextIP().getRouterID()]?.add(pack) //desde router, obtengo el mapa de outputBuffers de sus vecinos, luego obtengo la cola del siguiente vecino en el camino de pack, y finalmente añado pack a esa cola
        }
    }

    fun checkNeighbours(router: Router, destinationRouterId: Int): Boolean {
        router.getRouterTable().forEach { r ->
            if (r.getRouterId() == destinationRouterId) {
                return true
            }
        }
        return false
    }

    fun resentPackagesToNeighbourRouter(actualRouter: Router) {
        if (actualRouter.getRouterTable().isEmpty()) {
            println("No hay vecinos para el router ${actualRouter.getRouterId()}")
            return
        }

        if (actualRouter.getOutputBuffer().isEmpty()) {
            println("No hay paquetes para reenviar en el router ${actualRouter.getRouterId()}")
            return
        }

        actualRouter.getOutputBuffer().forEach { (routerId, queue) ->
            val packageDto = actualRouter.getOutputBuffer()[routerId]?.peek()
            val nextRouterId = packageDto?.getDestinationIP()
                ?.let { dijkstraService.nextVertexInShortestPath(actualRouter.getRouterId(), it.getRouterID()) }
            actualRouter.getRouterTable().forEach { neighbour ->
                if (neighbour.getRouterId() == routerId && nextRouterId != null && nextRouterId == neighbour.getRouterId()) {
                    println("Enviando paquete ${packageDto.getPackageId()} al router ${neighbour.getRouterId()} desde el router ${actualRouter.getRouterId()}")
                    WebSimulator.getInstance()
                        .getRouterList()
                        .find { router -> router.getRouterId() == neighbour.getRouterId() }
                        ?.getInputBuffer()
                        ?.add(updatePackageActualIpAndPollQueue(neighbour, queue))
                }
            }
        }
    }

    private fun updatePackageActualIpAndPollQueue(actualRouter: Router, queue: Queue<Package>) : Package? {
        val pack = queue.peek()
        pack?.let {
            it.getActualIP().setRouterID(actualRouter.getRouterId())
            queue.poll()
        }
        return pack
    }

    fun readInputBufferAndProcessPackages(actualRouter: Router) {
        if (actualRouter.getInputBuffer().isEmpty()) {
            println("No hay paquetes para procesar en el router ${actualRouter.getRouterId()}")
            return
        }

        val lastPackage = actualRouter.getInputBuffer().poll()

        if (lastPackage.getDestinationIP().getRouterID() == actualRouter.getRouterId()) {
            println("El paquete ${lastPackage.getPackageId()} llego a su destino")
            actualRouter.getOutputBufferTerminal()[lastPackage.getDestinationIP().getTerminalId()]?.add(lastPackage)
            return
        }

        if (actualRouter.getOutputBuffer().containsKey(lastPackage.getDestinationIP().getRouterID())) {
            println("El paquete ${lastPackage.getPackageId()} agregado al outputBuffer para enviarlo a su destino ${lastPackage.getDestinationIP().getRouterID()}")
            actualRouter.getOutputBuffer()[lastPackage.getDestinationIP().getRouterID()]?.add(lastPackage)
            return
        }

    }

}