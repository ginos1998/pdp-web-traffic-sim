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
        val neighbour = routerServices.checkNeigbourghs(router, page.getDestinationRouterId())

        if (page.getOriginRouterId() == page.getDestinationRouterId()) {
            router.getTerminalTable()[page.getDestinationIP().getTerminalId() - 1].getReceivedPages().add(page)
        } else {
            val packages = page.getPageContent().chunked(ab).fold(1) { acc, substring ->
                val pack = routerServices.createPackage(substring, page, acc)
                if (neighbour) {
                    pack.setNextIP(page.getDestinationIP())
                    routerServices.queuePackages(router, pack)
                }
                acc + 1
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