package controllers

import models.Package
import services.RouterServices
import models.Router
import models.Page


class RouterController (
    private var routerServices: RouterServices = RouterServices()
)
{
    fun receivePages(routers: ArrayList<Router>, page: Page){
        routerServices.addPage(routers[page.getOriginRouterId()-1], page)  //le resto 1 al ID origen pq el array list indexa desde el 0
    }

    fun pageSegmentation(routers: ArrayList<Router>, page: Page, ab: Int){
        var i = 0
        var id = 1
        var pack: Package

        if(page.getOriginRouterId() == page.getDestinationRouterId()){
            //directamente mandamos la pagina a la terminal que corresponda
            routers[page.getDestinationRouterId()].getTerminalTable()[page.getDestinationIP().getTerminalId()].setReceivedPage(page) //esta linea setea "received page con la pagina recibida"
        } else {
            while(i < page.getPageContent().length){
                val supLimit = minOf(i+ab, page.getPageContent().length)
                val substring = page.getPageContent().substring(i, supLimit)
                pack = routerServices.createPackage(substring, page, id)
                routerServices.sendPackage(routers[page.getOriginRouterId()], pack)
                i += ab
                id ++
            }
        }
    }

}