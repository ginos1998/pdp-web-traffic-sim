package controllers

import services.RouterServices
import models.Router
import models.Page

class RouterController (
    private var routerServices: RouterServices = RouterServices()
)
{
    fun receivePages(router: Router, page: Page){
        routerServices.addPage(router, page)
    }
}