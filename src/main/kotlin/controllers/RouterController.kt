package controllers

import models.Page
import models.Router
import models.Package
import services.RouterServices
import services.TerminalService
import utils.ConsoleColors
import java.util.Queue


class RouterController  (
    private var routerServices: RouterServices,
    private var terminalService: TerminalService,
)
{
    constructor():
            this(RouterServices(), TerminalService())

    fun resentPackagesToNeighbourRouter(actualRouter: Router) {
        routerServices.resentPackagesToNeighbourRouter(actualRouter)
    }

    fun readInputBufferAndProcessPackages(actualRouter: Router) {
        routerServices.readInputBufferAndProcessPackages(actualRouter)
    }
    
    fun resentPackagesOrBuildPage(actualRouter: Router) {
        actualRouter.getOutputBufferTerminal().forEach { (terminalId, queue) ->
            if (arePackagesInDestinyAndReadyToBuildPage(actualRouter, queue)) {
                println("${ConsoleColors.GREEN}El router ${actualRouter.getRouterName()} esta listo para construir una pagina y enviarla a la terminal $terminalId..${ConsoleColors.RESET}")
                buildPage(queue)?.let { terminalService.receivePage(it) }
            }
        }

    }

    private fun arePackagesInDestinyAndReadyToBuildPage(actualRouter: Router, queue: Queue<Package>): Boolean {
        return routerServices.arePackagesInDestinyAndReadyToBuildPage(actualRouter, queue)
    }

    private fun isPackageInDestiny(actualRouter: Router): Boolean {
        return routerServices.isPackageInDestiny(actualRouter)
    }

    private fun isReadyToBuildPage(actualRouter: Router): Boolean {
        return routerServices.isReadyToBuildPage(actualRouter)
    }
    
    private fun buildPage(queue: Queue<Package>) : Page? {
        return routerServices.buildPage(queue)
    }

    fun receivePages(router: Router, page: Page){
        routerServices.receivePages(router, page)
    }

    fun pageSegmentation(router: Router, page: Page, ab: Int){
        routerServices.pageSegmentation(router, page, ab)
    }

    fun sendPackages(router: Router, id: Int){
        router.getRouterTable().forEach {
                r -> if(r.getRouterId() == id) {
            router.getOutputBuffer()[id]?.let { r.setInputBuffer(it) }
        }
        }
    }

}