package controllers

import services.AdminService

class AdminController() {
    private val adminService: AdminService = AdminService();

    fun readFromExcelFile(filepath: String) {
        adminService.readFromExcelFile(filepath)
    }
}