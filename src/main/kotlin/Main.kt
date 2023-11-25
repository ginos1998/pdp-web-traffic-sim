import controllers.AdminController

fun main() {

    val filepath = "src/main/resources/test.xlsx"

    val adminController = AdminController()
    val webSimulator = adminController.readFromExcelFile(filepath)

    if (webSimulator.getRouterList().isNotEmpty() && webSimulator.getTerminalList().isNotEmpty()) {
        webSimulator.getRouterList().forEach { router ->
            println("Router: ${router.getRouterName()} (${router.getRouterId()})")
            router.getRouterTable().forEach { neighbour ->
                println("Vecino: ${neighbour.getRouterName()} (${neighbour.getRouterId()})")
            }
            println("----------------------------------")
        }

        webSimulator.getTerminalList().forEach { terminal ->
            println("Terminal: ${terminal.getTerminalName()} (${terminal.getTerminalId()})")
        }
    }
}
