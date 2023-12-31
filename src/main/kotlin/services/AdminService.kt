package services

import models.Router
import models.Terminal
import models.dtos.WebSimulator
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import utils.CommonFunctions
import java.io.FileInputStream
import java.util.*

class AdminService {
    private val sheetRouter: Int = 0
    private val sheetNeighbours: Int = 1
    private val sheetTerminal: Int = 2
    private val rowStartReading: Int = 2
    // Router
    private val colRouterName: Int = 0
    private val colRouterId: Int = 1
    // vecinos
    private val colNeighboursRouterId = 0
    private val colNeighboursNeighbourId = 1
    // Terminal
    private val colTerminalName: Int = 0
    private val colTerminalId: Int = 1
    private val colTerminalRouterId: Int = 2

    /**
     * Lee el archivo de excel y crea los routers y terminales.
     * @param filepath: String - Ruta del archivo de excel.
     * @return WebSimulator - Objeto con los routers y terminales.
     */
    fun readFromExcelFile(filepath: String) {
        try {
            val inputStream = FileInputStream(filepath)
            val xlWb = WorkbookFactory.create(inputStream)

            val routerList: MutableList<Router> = readRoutersFromFile(xlWb)
            mapRouterNeighbours(xlWb, routerList)
            WebSimulator.getInstance().setRouterList(routerList)

            val terminalList: MutableList<Terminal> = readTerminalsFromFile(xlWb)
            WebSimulator.getInstance().setTerminalList(terminalList)

            inputStream.close()
            xlWb.close()
        } catch (e: Exception) {
            println("Ha ocurrido un error al leer el archivo de excel: ${e.message}")
        }

    }

    /**
     * Lee los routers del archivo de excel y los guarda en una lista.
     * @param xlWb: Workbook - Archivo de excel.
     * @return MutableList<Router> - Lista de routers.
     */
    private fun readRoutersFromFile(xlWb: Workbook): MutableList<Router> {
        val routerList: MutableList<Router> = mutableListOf()
        var eof = false
        var nextRow: Int = rowStartReading
        val sheet = xlWb.getSheetAt(sheetRouter)
        while (!eof) {
            if (CommonFunctions.isCellEmpty(sheet, colRouterName, nextRow) && CommonFunctions.isCellEmpty(sheet, colRouterId, nextRow)) {
                val routerName = sheet.getRow(nextRow).getCell(colRouterName).toString()
                val routerId = sheet.getRow(nextRow).getCell(colRouterId).toString().toFloat().toInt()

                if (CommonFunctions.hasData(routerName) && CommonFunctions.hasData(routerId)) {
                    val router = Router(routerId, routerName)
                    routerList.add(router)
                    initDijkstraVertexes(routerId)
                }
            } else {
                eof = true
            }

            nextRow++
        }

        return routerList
    }

    /**
     * Mapea los vecinos de cada router.
     * @param xlWb: Workbook - Archivo de excel.
     * @param routerList: MutableList<Router> - Lista de routers.
     */
    private fun mapRouterNeighbours(xlWb: Workbook, routerList: MutableList<Router>) {
        if (!CommonFunctions.hasData(routerList)) {
            println("routerList tiene no datos, por lo cual no se pueden crear los vecinos.")
            return
        }

        var eof = false
        var nextRow: Int = rowStartReading
        val sheet = xlWb.getSheetAt(sheetNeighbours)
        while (!eof) {
            if (CommonFunctions.isCellEmpty(sheet, colNeighboursRouterId, nextRow)
                && CommonFunctions.isCellEmpty(sheet, colNeighboursNeighbourId, nextRow))
            {
                val routerId = sheet.getRow(nextRow).getCell(colNeighboursRouterId).toString().toFloat().toInt()
                val neighbourId = sheet.getRow(nextRow).getCell(colNeighboursNeighbourId).toString().toFloat().toInt()

                if (CommonFunctions.hasData(routerId) && CommonFunctions.hasData(neighbourId)) {

                    routerList.find { router -> router.getRouterId() == routerId }?.getRouterTable()?.add(
                        routerList.find { router -> router.getRouterId() == neighbourId }!!
                    )

                    routerList.forEach { router ->
                        if (router.getRouterId() == routerId) {
                            router.getOutputBuffer()[neighbourId] = LinkedList()
                        }
                    }

                    initDijkstraEdges(routerId, neighbourId)
                }
            } else {
                eof = true
            }

            nextRow++
        }

    }

    /**
     * Lee los terminales del archivo de excel y los guarda en una lista.
     * @param xlWb: Workbook - Archivo de excel.
     * @return MutableList<Terminal> - Lista de terminales.
     */
    private fun readTerminalsFromFile(xlWb: Workbook): MutableList<Terminal> {
        val terminalList: MutableList<Terminal> = mutableListOf()
        var eof = false
        var nextRow: Int = rowStartReading
        val sheet = xlWb.getSheetAt(sheetTerminal)
        while (!eof) {
            if (CommonFunctions.isCellEmpty(sheet, colTerminalName, nextRow)
                && CommonFunctions.isCellEmpty(sheet, colTerminalId, nextRow)
                && CommonFunctions.isCellEmpty(sheet, colTerminalRouterId, nextRow))
            {
                val terminalName = sheet.getRow(nextRow).getCell(colTerminalName).toString()
                val terminalId = sheet.getRow(nextRow).getCell(colTerminalId).toString().toFloat().toInt()
                val terminalRouterId = sheet.getRow(nextRow).getCell(colTerminalRouterId).toString().toFloat().toInt()

                if (CommonFunctions.hasData(terminalName) && CommonFunctions.hasData(terminalId)
                    && CommonFunctions.hasData(terminalRouterId))
                {
                    val terminal = Terminal(terminalId, terminalName)
                    terminalList.add(terminal)
                    WebSimulator.getInstance().getRouterList()
                        .find { router -> router.getRouterId() == terminalRouterId }
                        ?.let { router -> router.getOutputBufferTerminal()[terminalId] = LinkedList() }
                }
            } else {
                eof = true
            }

            nextRow++
        }

        return terminalList
    }

    /**
     * Inicializa la lista de aristas del algoritmo de Dijkstra.
     */
    private fun initDijkstraEdges(routerId: Int, neighbourId: Int) {
        val routerPathList: MutableList<Int> = mutableListOf()
        routerPathList.add(routerId)
        routerPathList.add(neighbourId)
        routerPathList.add(getRandomWeight()) // 1 es el peso del camino

        WebSimulator.getInstance().getGraph().getEdges().add(routerPathList)
    }

    private fun getRandomWeight(): Int {
        return (1..10).random()
    }

    /**
     * Inicializa la lista de vertices del algoritmo de Dijkstra.
     */
    private fun initDijkstraVertexes(routerId: Int) {
        WebSimulator.getInstance().getGraph().getVertexes().add(routerId)
    }


}