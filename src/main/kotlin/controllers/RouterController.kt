package controllers

import models.Page
import models.Router
import models.Package
import services.RouterServices
import services.TerminalService
import java.util.Queue


class RouterController  (
    private var routerServices: RouterServices,
    private var terminalService: TerminalService
)
{
    constructor():
            this(RouterServices(), TerminalService())
    
    fun resentPackagesOrBuildPage(actualRouter: Router, page: Page, ab: Int) {
        if (actualRouter.getOutputBuffer().isNotEmpty()) {
            if (isPackageInDestiny(actualRouter) && isReadyToBuildPage(actualRouter) ) {
                buildPage(actualRouter)?.let { terminalService.receivePage(it) }
            } else {
                pageSegmentation(actualRouter, page, ab)
            }
        } else {
            pageSegmentation(actualRouter, page, ab)
        }
    }
    private fun isPackageInDestiny(actualRouter: Router): Boolean {
        return routerServices.isPackageInDestiny(actualRouter)
    }

    private fun isReadyToBuildPage(actualRouter: Router): Boolean {
        return routerServices.isReadyToBuildPage(actualRouter)
    }
    
    private fun buildPage(actualRouter: Router) : Page? {
        return routerServices.buildPage(actualRouter)
    }

    fun receivePages(router: Router, page: Page){
        routerServices.addPage(router, page)  //le resto 1 al ID origen pq el array list indexa desde el 0
    }

    private fun pageSegmentation(router: Router, page: Page, ab: Int){
        page.getPageContent().chunked(ab).fold(1) { acc, substring ->
            val pack = routerServices.createPackage(substring, page, acc)
            val neighbour = routerServices.checkNeigbourghs(router, page.getDestinationRouterId())
            if (neighbour) {
                pack.setNextIP(page.getDestinationIP())
                routerServices.queuePackages(router, pack)
            }
            acc + 1
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