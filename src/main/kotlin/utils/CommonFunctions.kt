package utils

import org.apache.poi.ss.usermodel.Sheet

class CommonFunctions {
    companion object{
        fun hasData(data: String): Boolean {
            return data.isNotBlank() && data.isNotEmpty()
        }

        fun hasData(data: Int): Boolean {
            return data != 0
        }

        fun isCellEmpty(sheet: Sheet, column: Int, nextRow: Int) : Boolean {
            return sheet.getRow(nextRow) != null && sheet.getRow(nextRow).getCell(column) != null
        }
    }
}