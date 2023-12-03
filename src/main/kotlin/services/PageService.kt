package services

import models.IP
import models.Page

class PageService {
    fun buildRandomPage(): Page {
        val originIP = IP(1, 1)
        val destinyIP = IP(2, 3)

        val msg = "Hola mundo"
        return Page(1, msg, msg.length, destinyIP, originIP)

    }
}