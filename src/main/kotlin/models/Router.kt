package models

import java.util.Queue

class Router (
    private var routerId: Int = 0,
    private var routerName: String = "",
    private var routerTable: List<Router>,
    private var terminalTable: List<Terminal>,
    private var inputBuffer: Queue<Page>,
    private var outputBuffer: Map<Int, Queue<Package>>, // key: routerId, value: queue of packages
    private var routerPath: Map<Terminal, List<Router>>
)
{
    // getters and setters
    fun getRouterId(): Int {
        return routerId
    }

    fun setRouterId(routerId: Int) {
        this.routerId = routerId
    }

    fun getRouterName(): String {
        return routerName
    }

    fun setRouterName(routerName: String) {
        this.routerName = routerName
    }

    fun getRouterTable(): List<Router> {
        return routerTable
    }

    fun setRouterTable(routerTable: List<Router>) {
        this.routerTable = routerTable
    }

    fun getTerminalTable(): List<Terminal> {
        return terminalTable
    }

    fun setTerminalTable(terminalTable: List<Terminal>) {
        this.terminalTable = terminalTable
    }

    fun getInputBuffer(): Queue<Page> {
        return inputBuffer
    }

    fun setInputBuffer(inputBuffer: Queue<Page>) {
        this.inputBuffer = inputBuffer
    }

    fun getOutputBuffer(): Map<Int, Queue<Package>> {
        return outputBuffer
    }

    fun setOutputBuffer(outputBuffer: Map<Int, Queue<Package>>) {
        this.outputBuffer = outputBuffer
    }

    fun getRouterPath(): Map<Terminal, List<Router>> {
        return routerPath
    }

    fun setRouterPath(routerPath: Map<Terminal, List<Router>>) {
        this.routerPath = routerPath
    }
}