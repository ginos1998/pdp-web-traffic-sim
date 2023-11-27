package services

import models.Page
import models.dtos.WebSimulator

class TerminalService {
    fun receivePage(page: Page) {
        WebSimulator.getInstance().getTerminalList().forEach { terminal ->
            if (terminal.getTerminalId() == page.getDestinationRouterId()) {
                terminal.getReceivedPageList().add(page)
            }
        }
    }
}