package services

import models.IP
import models.Page
import models.dtos.WebSimulator

class PageService {
    private var pageId = 1
    fun buildRandomPage(): Page {
        val originIP = getRandomIPWithExcept(-1)
        val destinyIP = getRandomIPWithExcept(originIP.getRouterID())
        val message = getRandomMessage()

        return Page(pageId++, message, message.length, destinyIP, originIP)
    }

    private fun getRandomIPWithExcept(exceptRouterId: Int): IP {
        val routerId = getRandomRouterId(exceptRouterId)
        val terminalId = getRandomTerminal(routerId)
        return IP(routerId,terminalId )
    }
    
    private fun getRandomRouterId(exceptRouterId: Int): Int {
        val routerIdList = WebSimulator.getInstance().getRouterList()
            .filter { router -> router.getRouterId() != exceptRouterId }
            .map { router -> router.getRouterId() }
        val index = routerIdList.indices.random()

        return routerIdList[index]
    }

    private fun getRandomTerminal(routerId: Int): Int {
        val routerTerminalList = WebSimulator.getInstance().getRouterList()
            .filter { router -> router.getRouterId() == routerId }
            .map { router -> router.getOutputBuffer() }
            .map { outputBuffer ->
                outputBuffer.map { ip -> ip.key }
            }.flatten()

        val index = routerTerminalList.indices.random()
        return routerTerminalList[index]
    }

    private fun getRandomMessage(): String {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val minWordLength = 5
        val maxWordLength = 25
        val wordLength = (minWordLength..maxWordLength).random()

        return (1..wordLength)
            .map { characters.random() }
            .joinToString("")
    }



}