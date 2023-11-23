import controllers.AdminController

fun main() {
    val filepath = "/home/ginos/Documentos/test.xlsx"

    val adminController = AdminController()
    adminController.readFromExcelFile(filepath)
}
