import controllers.AdminController
import controllers.RouterController
import models.IP
import models.dtos.WebSimulator
import models.Package
import java.util.*

fun main() {

    val filepath = "src/main/resources/test.xlsx"

    val adminController = AdminController()
    val routerController = RouterController()

    adminController.readFromExcelFile(filepath)

    if (WebSimulator.getInstance().getRouterList().isNotEmpty() && WebSimulator.getInstance().getTerminalList().isNotEmpty()) {
        WebSimulator.getInstance().getRouterList().forEach { router ->
            println("Router: ${router.getRouterName()} (${router.getRouterId()})")
            router.getRouterTable().forEach { neighbour ->
                println("Vecino: ${neighbour.getRouterName()} (${neighbour.getRouterId()})")
            }
            println("----------------------------------")
        }

        WebSimulator.getInstance().getTerminalList().forEach { terminal ->
            println("Terminal: ${terminal.getTerminalName()} (${terminal.getTerminalId()})")
        }
    }


    val ipDemo = IP(1, 1)
    val packageDemo = Package(1, "bokaaa", 1, 1, 1, ipDemo, ipDemo, ipDemo)
    val packageQueue: Queue<Package> = LinkedList()
    packageQueue.add(packageDemo)
    val map: Map<Int, Queue<Package>> = mapOf(1 to packageQueue)
    WebSimulator.getInstance().getRouterList()[0].setOutputBuffer(map)

    routerController.resentPackagesOrBuildPage(WebSimulator.getInstance().getRouterList()[0])

    if (WebSimulator.getInstance().getTerminalList()[0].getReceivedPageList().isNotEmpty()) {
        println("nombre " + WebSimulator.getInstance().getTerminalList()[0].getReceivedPageList()[0].getPageName()) // bokaaa
    }


}
