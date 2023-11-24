package models

class Terminal (
    private var terminalId: Int = 0,
    private var terminalName: String = "",
    private var page: Page,
    private var receivedPage: Page,
    private var router: Router
)
{
    // getters and setters
    fun getTerminalId(): Int {
        return terminalId
    }

    fun setTerminalId(terminalId: Int) {
        this.terminalId = terminalId
    }

    fun getTerminalName(): String {
        return terminalName
    }

    fun setTerminalName(terminalName: String) {
        this.terminalName = terminalName
    }

    fun getPage(): Page {
        return page
    }

    fun setPage(page: Page) {
        this.page = page
    }

    fun getRouter(): Router {
        return router
    }

    fun setRouter(router: Router) {
        this.router = router
    }

    fun getReceivedPage(): Page{
        return receivedPage
    }

    fun setReceivedPage(page: Page){
        this.receivedPage = page
    }
}