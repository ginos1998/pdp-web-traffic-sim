import controllers.AdminController
import controllers.RouterController
import models.*
import models.dtos.WebSimulator
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

    val routerController = RouterController()

    val routers = ArrayList<Router>()

    val ip_o = IP(1,1)
    val ip_d = IP(2,1)

    val ab = 3 //2 bytes por segundo

    val outBuffR12: Queue<Package> = LinkedList()
    val outMapR1 = mutableMapOf<Int, Queue<Package>>()
    outMapR1[2] = outBuffR12

    val page = Page(1, "Hola mundo",0,ip_d,ip_o)
    page.setSize(page.getPageContent().length)

    val router1 = Router(1,"", ArrayList(), ArrayList(),LinkedList(),LinkedList(), outMapR1, mutableMapOf())
    val router2 = Router(2,"", ArrayList(), ArrayList(),LinkedList(),LinkedList(), mutableMapOf(), mutableMapOf())

    val term1 = Terminal(1,"", LinkedList(), router1)
    val term2 = Terminal(2,"", LinkedList(), router1)
    val term3 = Terminal(1,"", LinkedList(), router2)

    val terminalRouter1 = ArrayList<Terminal>()
    val terminalRouter2 = ArrayList<Terminal>()

    terminalRouter1.add(term1)
    terminalRouter1.add(term2)
    terminalRouter2.add(term3)

    router1.setTerminalTable(terminalRouter1)

    routers.add(router1)
    routers.add(router2)

    routerController.receivePages(router1, page) //envio la pagina desde una terminal conectada al router1 hacia este ultimo
    routerController.pageSegmentation(router1, page, ab)
    println("Segmentado...")
}
