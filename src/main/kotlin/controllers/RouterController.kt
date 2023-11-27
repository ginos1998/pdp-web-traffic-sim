package controllers

import models.Page
import models.Router
import services.RouterServices
import services.TerminalService


class RouterController  (
    private var routerServices: RouterServices,
    private var terminalService: TerminalService
)
{
    constructor():
            this(RouterServices(), TerminalService())
    
    fun resentPackagesOrBuildPage(actualRouter: Router) {
        if (actualRouter.getOutputBuffer().isNotEmpty()) {
            if (isPackageInDestiny(actualRouter) && isReadyToBuildPage(actualRouter) ) {
                buildPage(actualRouter)?.let { terminalService.receivePage(it) }
            } else {
                // reenviar a router vecino
            }
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

}