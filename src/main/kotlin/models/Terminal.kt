package models

import java.util.*

class Terminal (
    private var terminalId: Int = 0,
    private var terminalName: String = "",
    private var receivedPages: Queue<Page>,
    private var router: Router
    )
{
    constructor(terminalId: Int, terminalName: String):
            this(terminalId, terminalName, LinkedList<Page>(), Router())

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

    fun getRouter(): Router {
        return router
    }

    fun setRouter(router: Router) {
        this.router = router
    }

    fun getReceivedPages(): Queue<Page>{
        return receivedPages
    }

    fun setReceivedPages(pages: Queue<Page>){
        this.receivedPages = pages
    }
}