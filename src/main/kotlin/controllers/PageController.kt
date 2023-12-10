package controllers

import models.Page
import services.PageService

class PageController (
    private var pageService: PageService
)
{
    constructor():
            this(PageService())
    fun buildRandomPage(): Page {
        return pageService.buildRandomPage()
    }
}