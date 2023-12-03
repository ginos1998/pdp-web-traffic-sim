import controllers.AdminController
import controllers.PageController
import controllers.RouterController
import models.*
import models.dtos.WebSimulator

fun main() {

    val filepath = "src/main/resources/test.xlsx"

    val adminController = AdminController()
    val routerController = RouterController()
    val pageController = PageController()

    adminController.readFromExcelFile(filepath)

    var i = 1
    var cicles = 1
    var endOfSim = false
    WebSimulator.getInstance().setCicleTime(2)

    while (!endOfSim) {
        WebSimulator.getInstance().getRouterList().forEach { router ->
            println("########## ROUTER ${router.getRouterName()} ##########")
            var somePage = Page()
            if (i++==1) {
                somePage = pageController.buildRandomPage()
            }
            val ab = 3 //3 bytes por segundo

            if (router.getRouterId() == somePage.getOriginIP().getRouterID()) {
                routerController.receivePages(router, somePage)
                routerController.pageSegmentation(router, somePage, ab)
            }

            routerController.resentPackagesOrBuildPage(router)
            routerController.resentPackagesToNeighbourRouter(router)
            routerController.readInputBufferAndProcessPackages(router)

            println("###############################################\n")

            Thread.sleep(1000)
        }

        if (cicles++ == WebSimulator.getInstance().getCicleTime()) {
            printPagesReceivedByTerminal()
            cicles = 1
            print("Han pasado dos ciclos de simulacion, continuar? (y/n): ")
            val input = readlnOrNull()
            if (!input.contentEquals("y")) {
                endOfSim = true
            }
        }
    }

}

fun printPagesReceivedByTerminal() {
    WebSimulator.getInstance().getTerminalList().forEach { terminal ->
        if (terminal.getReceivedPages().isEmpty()) {
            return@forEach
        }

        println("########## TERMINAL ${terminal.getTerminalName()} ##########")
        terminal.getReceivedPages().forEach { page ->
            println("Pagina ${page.getPageId()} recibida: ${page.getPageContent()}")
        }
        println("###############################################\n")
    }
}
