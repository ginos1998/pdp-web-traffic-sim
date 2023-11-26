package models

class Terminal (
    private var terminalId: Int = 0,
    private var terminalName: String = "",
    private var page: Page,
    private var router: Router,
    private var receivedPageList: MutableList<Page> = mutableListOf(),
)
{
    constructor(terminalId: Int, terminalName: String):
            this(terminalId, terminalName, Page(), Router())
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

    fun getReceivedPageList(): MutableList<Page> {
        return receivedPageList
    }

    fun setReceivedPageList(receivedPageList: MutableList<Page>) {
        this.receivedPageList = receivedPageList
    }

}