package services

import models.Router
import models.Package
import models.Page

class RouterServices {
    fun isPackageInDestiny(actualRouter: Router): Boolean {
        var inDestiny = false

        actualRouter.getOutputBuffer().forEach { (routerId, queue) ->
            if (queue.isNotEmpty() && actualRouter.getRouterId() == routerId) {
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

    fun buildPage(actualRouter: Router): Page? {
        val somePackage = actualRouter.getOutputBuffer()[actualRouter.getRouterId()]?.peek()
        var pageName = ""
        actualRouter.getOutputBuffer()[actualRouter.getRouterId()]
            ?.forEach { pack ->
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
    fun addPage(router: Router, page: Page){
        router.getReceivedPages().add(page)
    }

    fun createPackage(substring: String, page: Page, id: Int): Package{
        return Package(id, substring, page.getPageId(), 5,page.getDestinationIP(),page.getOriginIP(), page.getOriginIP())
    }

    fun queuePackages(router: Router, pack: Package){
        if(pack.getActualIP().getRouterID() != pack.getDestinationIP().getRouterID()){
            router.getOutputBuffer()[pack.getNextIP().getRouterID()]?.add(pack) //desde router, obtengo el mapa de outputBuffers de sus vecinos, luego obtengo la cola del siguiente vecino en el camino de pack, y finalmente añado pack a esa cola
        }
    }

    fun checkNeigbourghs(router: Router, destinationRouterId: Int): Boolean {
        router.getRouterTable().forEach { r ->
            if (r.getRouterId() == destinationRouterId) {
                return true
            }
        }
        return false
    }
}