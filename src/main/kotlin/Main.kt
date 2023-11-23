import models.*
import controllers.*
import java.util.*
import kotlin.collections.ArrayList

fun main() {
    val routerController = RouterController()

    val page = Page(1, "Hola mundo",0,1,2)
    page.setSize(page.getPageContent().length)

    val router = Router(1,"", ArrayList(), ArrayList(),LinkedList(),LinkedList(), mutableMapOf(), mutableMapOf())

    routerController.receivePages(router, page)

    val p1 = router.getReceivedPages().poll()

    println("Id de la pagina: ${p1.getPageId()}")
    println("Contenido de la pagina: ${p1.getPageContent()}")
    println("Peso de la pagina: ${p1.getSize()}")
}