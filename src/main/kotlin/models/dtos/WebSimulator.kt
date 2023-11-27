package models.dtos

import models.Router
import models.Terminal

open class WebSimulator (
    private var routerList: MutableList<Router> = mutableListOf(),
    private var terminalList: MutableList<Terminal> = mutableListOf(),
    private var cicleTime: Int = 0

)
{
    // static field of WebSimulator class to be used as a singleton
    companion object {
        private var instance: WebSimulator? = null
        fun getInstance(routerList: MutableList<Router>, terminalList: MutableList<Terminal>, cicleTime: Int): WebSimulator {
            if (instance == null) {
                instance = WebSimulator(routerList, terminalList, cicleTime)
            }
            return instance!!
        }

        fun getInstance(routerList: MutableList<Router>, terminalList: MutableList<Terminal>): WebSimulator {
            if (instance == null) {
                instance = WebSimulator(routerList, terminalList)
            }
            return instance!!
        }

        fun getInstance(): WebSimulator {
            if (instance == null) {
                instance = WebSimulator()
            }
            return instance!!
        }
    }

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