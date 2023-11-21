package models

class IP (
    private var routerID: Int,
    private var terminalId: Int
)
{
    // getters and setters
    fun getRouterID(): Int {
        return routerID
    }

    fun setRouterID(routerID: Int) {
        this.routerID = routerID
    }

    fun getTerminalId(): Int {
        return terminalId
    }

    fun setTerminalId(terminalId: Int) {
        this.terminalId = terminalId
    }
}