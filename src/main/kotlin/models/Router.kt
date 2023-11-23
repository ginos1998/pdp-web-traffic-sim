package models

import java.util.Queue

class Router (
        private var routerId: Int = 0,
        private var routerName: String = "",
        private var routerTable: ArrayList<Router> = ArrayList(),
        private var terminalTable: ArrayList<Terminal> = ArrayList(),
        private var pagesReceived: Queue<Page>, //este atributo almacena la paginas nuevas que llegan al router para posteriormente poder segmentarlas
        private var inputBuffer: Queue<Package>,
        private var outputBuffer: Map<Int, Queue<Package>>, // key: routerId, value: queue of packages
        private var routerPath: Map<Terminal, List<Router>>
)
{
    // getters and setters
    fun getRouterId(): Int {
        return this.routerId
    }

    fun setRouterId(routerId: Int) {
        this.routerId = routerId
    }

    fun getRouterName(): String {
        return this.routerName
    }

    fun setRouterName(routerName: String) {
        this.routerName = routerName
    }

    fun getRouterTable(): ArrayList<Router> {
        return this.routerTable
    }

    fun setRouterTable(routerTable: ArrayList<Router>) {
        this.routerTable = routerTable
    }

    fun getTerminalTable(): ArrayList<Terminal> {
        return this.terminalTable
    }

    fun setTerminalTable(terminalTable: ArrayList<Terminal>) {
        this.terminalTable = terminalTable
    }

    fun getInputBuffer(): Queue<Package> {
        return this.inputBuffer
    }

    fun setInputBuffer(inputBuffer: Queue<Package>) {
        this.inputBuffer = inputBuffer
    }

    fun getOutputBuffer(): Map<Int, Queue<Package>> {
        return this.outputBuffer
    }

    fun setOutputBuffer(outputBuffer: Map<Int, Queue<Package>>) {
        this.outputBuffer = outputBuffer
    }

    fun getRouterPath(): Map<Terminal, List<Router>> {
        return this.routerPath
    }

    fun setRouterPath(routerPath: Map<Terminal, List<Router>>) {
        this.routerPath = routerPath
    }

    fun getReceivedPages(): Queue<Page>{
        return this.pagesReceived
    }

    fun setPagesReceived(pagesReceived: Queue<Page>){
        this.pagesReceived = pagesReceived
    }
}