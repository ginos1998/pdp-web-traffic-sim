package controllers

import models.Package
import services.RouterServices
import models.Router
import models.Page
import java.util.Queue


class RouterController (
    private var routerServices: RouterServices = RouterServices()
)
{
    fun receivePages(router: Router, page: Page){
        routerServices.addPage(router, page)  //le resto 1 al ID origen pq el array list indexa desde el 0
    }

    fun pageSegmentation(router: Router, page: Page, ab: Int){
        var i = 0
        var id = 1
        var pack: Package

        val neighbour = routerServices.checkNeigbourghs(router, page.getDestinationRouterId())

        if(page.getOriginRouterId() == page.getDestinationRouterId()){
            //directamente mandamos la pagina a la terminal que corresponda
            router.getTerminalTable()[page.getDestinationIP().getTerminalId()-1].getReceivedPages().add(page) //esta linea setea "received page con la pagina recibida"
        } else {
            while(i < page.getPageContent().length){
                val supLimit = minOf(i+ab, page.getPageContent().length)
                val substring = page.getPageContent().substring(i, supLimit)
                pack = routerServices.createPackage(substring, page, id)
                if (neighbour){
                    pack.setNextIP(page.getDestinationIP())
                    routerServices.queuePackages(router, pack)
                }
                i += ab
                id ++
            }
        }
    }

    fun sendPackages(router: Router, id: Int){
        router.getRouterTable().forEach {
            r -> if(r.getRouterId() == id) {
            router.getOutputBuffer()[id]?.let { r.setInputBuffer(it) }
            }
        }
    }

}