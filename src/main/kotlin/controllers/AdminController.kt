package controllers

import models.dtos.WebSimulator
import services.AdminService

class AdminController() {
    private val adminService: AdminService = AdminService()

    fun readFromExcelFile(filepath: String) : WebSimulator {
        return adminService.readFromExcelFile(filepath)
    }
}