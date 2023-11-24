package models.dtos

import models.Router
import models.Terminal

class WebSimulator (
    private var routerList: MutableList<Router> = mutableListOf(),
    private var terminalList: MutableList<Terminal> = mutableListOf(),
    private var cicleTime: Int = 0
)
{
    constructor():
            this(mutableListOf(), mutableListOf())
    // getters and setters
    fun getRouterList(): MutableList<Router> {
        return routerList
    }

    fun setRouterList(routerList: MutableList<Router>) {
        this.routerList = routerList
    }

    fun getTerminalList(): MutableList<Terminal> {
        return terminalList
    }

    fun setTerminalList(terminalList: MutableList<Terminal>) {
        this.terminalList = terminalList
    }

    fun getCicleTime(): Int {
        return cicleTime
    }

    fun setCicleTime(cicleTime: Int) {
        this.cicleTime = cicleTime
    }

}