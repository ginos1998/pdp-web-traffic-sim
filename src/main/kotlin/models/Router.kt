package models

import java.util.LinkedList
import java.util.Queue

class Router (
    private var routerId: Int = 0,
    private var routerName: String = "",
    private var routerTable: MutableList<Router> = ArrayList(),
    private var terminalTable: ArrayList<Terminal> = ArrayList(),
    private var pagesReceived: Queue<Page>, //este atributo almacena la paginas nuevas que llegan al router para posteriormente poder segmentarlas
    private var inputBuffer: Queue<Package>,
    private var outputBuffer: MutableMap<Int, Queue<Package>> = mutableMapOf(), // key: routerId, value: queue of packages
    private var outputBufferTerminal: MutableMap<Int, Queue<Package>>, // key: terminalId, value: queue of packages
    private var routerPath: MutableMap<Router, LinkedList<Router>>
)
{
    constructor():
            this(0, "", mutableListOf(), ArrayList(), LinkedList(), LinkedList(), mutableMapOf(), mutableMapOf(), mutableMapOf())
    constructor(routerId: Int, routerName: String):
            this(routerId, routerName, mutableListOf(), ArrayList(), LinkedList(), LinkedList(), mutableMapOf(), mutableMapOf(),  mutableMapOf())

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

    fun getRouterTable(): MutableList<Router> {
        return routerTable
    }

    fun setRouterTable(routerTable: MutableList<Router>) {
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

    fun getOutputBuffer(): MutableMap<Int, Queue<Package>> {
        return this.outputBuffer
    }

    fun setOutputBuffer(outputBuffer: MutableMap<Int, Queue<Package>>) {
        this.outputBuffer = outputBuffer
    }

    fun getRouterPath(): MutableMap<Router, LinkedList<Router>> {
        return this.routerPath
    }

    fun setRouterPath(routerPath: MutableMap<Router, LinkedList<Router>>) {
        this.routerPath = routerPath
    }

    fun getReceivedPages(): Queue<Page>{
        return this.pagesReceived
    }

    fun setPagesReceived(pagesReceived: Queue<Page>){
        this.pagesReceived = pagesReceived
    }

    fun getOutputBufferTerminal(): MutableMap<Int, Queue<Package>> {
        return this.outputBufferTerminal
    }

    fun setOutputBufferTerminal(outputBufferTerminal: MutableMap<Int, Queue<Package>>) {
        this.outputBufferTerminal = outputBufferTerminal
    }

}