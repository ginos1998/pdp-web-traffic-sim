package services

import models.Router
import models.Terminal
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import utils.CommonFunctions
import java.io.FileInputStream

class AdminService {
    private val SHEET_NUMBER: Int = 0
    // Router
    private val COL_ROUTER_NAME: Int = 0
    private val COL_ROUTER_ID: Int = 1
    private val ROW_ROUTER_INNIT: Int = 2
    // vecinos
    private val COL_VECINOS_ROUTER_ID = 3
    private val COL_VECINOS_VECINO_ID = 4
    // Terminal
    private val COL_TERMINAL_NAME: Int = 6
    private val COL_TERMINAL_ID: Int = 7
    private val COL_TERMINAL_ROUTER_ID: Int = 8

    fun readFromExcelFile(filepath: String) {
        val inputStream = FileInputStream(filepath)
        val xlWb = WorkbookFactory.create(inputStream)
        val routerList: MutableList<Router> = mutableListOf()
        val neighbourMap: MutableMap<Int, Int> = mutableMapOf()
        val terminalList: MutableList<Terminal> = mutableListOf()
        val routerTerminalMap: MutableMap<Int, Int> = mutableMapOf()


        var eof: Boolean = false
        var eorRouter: Boolean = false
        var eorNeighbour: Boolean = false
        var eorTerminal: Boolean = false
        var nextRow: Int = ROW_ROUTER_INNIT

        while (!eof) {
            val sheet = xlWb.getSheetAt(SHEET_NUMBER)

            if (CommonFunctions.isCellEmpty(sheet, COL_ROUTER_NAME, nextRow) && CommonFunctions.isCellEmpty(sheet, COL_ROUTER_ID, nextRow)) {
                val routerName = sheet.getRow(nextRow).getCell(COL_ROUTER_NAME).toString()
                val routerId = sheet.getRow(nextRow).getCell(COL_ROUTER_ID).toString().toFloat().toInt()

                if (CommonFunctions.hasData(routerName) && CommonFunctions.hasData(routerId)) {
                    val router = Router(routerId, routerName)
                    routerList.add(router)
                }
            } else {
                eorRouter = true
            }

            if (CommonFunctions.isCellEmpty(sheet, COL_VECINOS_ROUTER_ID, nextRow)
                && CommonFunctions.isCellEmpty(sheet, COL_VECINOS_VECINO_ID, nextRow))
            {
                val routerId = sheet.getRow(nextRow).getCell(COL_VECINOS_ROUTER_ID).toString().toFloat().toInt()
                val neighbourId = sheet.getRow(nextRow).getCell(COL_VECINOS_VECINO_ID).toString().toFloat().toInt()
                println("leyendo routerId=$routerId y neighbourId=$neighbourId")
                if (CommonFunctions.hasData(routerId) && CommonFunctions.hasData(neighbourId)) {
                    neighbourMap[routerId] = neighbourId
                    neighbourMap.forEach() { (routerId, neighbourId) ->
                        println(String.format("se agrego routerId=%s y neighbourId=%s", routerId, neighbourId))
                    }
                }
            } else {
                eorNeighbour = true
            }

            if (CommonFunctions.isCellEmpty(sheet, COL_TERMINAL_NAME, nextRow)
                && CommonFunctions.isCellEmpty(sheet, COL_TERMINAL_ID, nextRow)
                && CommonFunctions.isCellEmpty(sheet, COL_TERMINAL_ROUTER_ID, nextRow))
            {
                val terminalName = sheet.getRow(nextRow).getCell(COL_TERMINAL_NAME).toString()
                val terminalId = sheet.getRow(nextRow).getCell(COL_TERMINAL_ID).toString().toFloat().toInt()
                val terminalRouterId = sheet.getRow(nextRow).getCell(COL_TERMINAL_ROUTER_ID).toString().toFloat().toInt()

                if (CommonFunctions.hasData(terminalName) && CommonFunctions.hasData(terminalId)
                    && CommonFunctions.hasData(terminalRouterId))
                {
                    val terminal = Terminal(terminalId, terminalName)
                    terminalList.add(terminal)
                    routerTerminalMap[terminalRouterId] = terminalId
                }
            } else {
                eorTerminal = true
            }

            nextRow++
            if (eorRouter && eorNeighbour && eorTerminal) {
                eof = true
            }
        }

//        neighbourMap.forEach{ (routerId, neighbourId) ->
//            println(String.format("routerId=%s y neighbourId=%s", routerId, neighbourId))
//        }
//
//        val routerIdSet = routerList.distinctBy { router -> router.getRouterId()  }
//        routerIdSet.forEach { router ->
//            println("leyendo " + router.getRouterName())
//            neighbourMap.forEach { (routerId, neighbourId) ->
//                println(String.format("valida routerId=%s y neighbourId=%s", routerId, neighbourId))
//                if (router.getRouterId() == routerId) {
//                    routerList.forEach { router2 ->
//                        if (router2.getRouterId() == neighbourId) {
//                            router.getRouterTable().add(router2)
//                        }
//                    }
//                }
//            }
//        }
//
//        println(routerList[0].getRouterName())
//        routerList[0].getRouterTable().forEach { router -> println(router.getRouterName()) }
    }

}