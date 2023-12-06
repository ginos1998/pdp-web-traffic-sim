import controllers.AdminController
import controllers.PageController
import controllers.RouterController
import models.*
import models.dtos.WebSimulator
import utils.ConsoleColors

fun main() {
    val filepath = "src/main/resources/test.xlsx"

    val adminController = AdminController()
    val routerController = RouterController()
    val pageController = PageController()

    adminController.readFromExcelFile(filepath)

    var cicles = 1
    var endOfSim = false
    WebSimulator.getInstance().setCicleTime(2)

    while (!endOfSim) {
        WebSimulator.getInstance().getRouterList().forEach { router ->
            println("${ConsoleColors.CYAN}########## ROUTER ${router.getRouterName()} ##########${ConsoleColors.RESET}")
            val somePage = pageController.buildRandomPage()

            val ab = 3 //3 bytes por segundo

            if (router.getRouterId() == somePage.getOriginIP().getRouterID()) {
                printPageToSend(somePage)
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
            print("${ConsoleColors.YELLOW}Han pasado dos ciclos de simulacion, continuar? (y/n): ${ConsoleColors.RESET}")
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

        println("${ConsoleColors.GREEN}########## TERMINAL ${terminal.getTerminalName()} ##########${ConsoleColors.RESET}")
        terminal.getReceivedPages().forEach { page ->
            println("${ConsoleColors.GREEN}Pagina ${page.getPageId()} recibida: ${page.getPageContent()}${ConsoleColors.RESET}")
        }
        println("${ConsoleColors.GREEN}###############################################\n${ConsoleColors.RESET}")
    }
}

fun printPageToSend(page: Page) {
    println("${ConsoleColors.BLUE}Pagina a enviar: ${page.getPageContent()} al router ${page.getDestinationIP().getRouterID()}${ConsoleColors.RESET}")
}

