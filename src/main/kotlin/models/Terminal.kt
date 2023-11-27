package models

import java.util.Queue
import models.Page
class Terminal (
    private var terminalId: Int = 0,
    private var terminalName: String = "",
    private var receivedPages: Queue<Page>,
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