package services

import models.Page
import models.Router

class RouterServices
{
    //Este metodo tendria que recibir por pasaje por referencia:
    //      * la cola de paginas de un router
    //      * la pagina que queremos agregar
    fun addPage(router: Router, page: Page){
        router.getReceivedPages().add(page)
    }
}