package services

import models.Page
import models.dtos.WebSimulator
import utils.ConsoleColors

class TerminalService {
    fun receivePage(page: Page) {
        WebSimulator.getInstance().getTerminalList().forEach { terminal ->
            if (terminal.getTerminalId() == page.getDestinationIP().getTerminalId()) {
                terminal.getReceivedPages().add(page)
                println("${ConsoleColors.GREEN}La terminal ${terminal.getTerminalName()} recibió la pagina ${page.getPageId()} del router ${page.getOriginRouterId()}${ConsoleColors.RESET}")
            }
        }
    }
}