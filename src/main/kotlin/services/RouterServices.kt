package services

import models.Package
import models.Page
import models.Router

class RouterServices
{
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
            //esta parte se tiene que revisar una vez que integremos las partes, ya que necesito saber como vamos a estructurar el mapa de caminos
            router.getOutputBuffer()[pack.getNextIP().getRouterID()]?.add(pack) //desde router, obtengo el mapa de outputBuffers de sus vecinos, luego obtengo la cola del siguiente vecino en el camino de pack, y finalmente añado pack a esa cola
        }
    }

    fun checkNeigbourghs(router:Router, id: Int): Boolean{
        return router.getOutputBuffer().containsKey(id)
    }
}