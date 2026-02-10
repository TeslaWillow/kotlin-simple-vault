package services

import interfaces.Exportable

class ExportService {

    fun processExport(item: Exportable) {
        val rawData = item.export()
        if (rawData.isEmpty()) {
            System.err.println("Export Error: No data found for this item.")
            return
        }

        println("Exporting data...")
        println("Result: $rawData")
    }

    fun executeExport(item: Exportable) {
        val data = item.export()

        // Guard Clause: Prevent exporting empty or corrupted data
        if (data.isBlank()) {
            System.err.println("Export Error: Item has no exportable data.")
            return
        }

        println("\n--- Exporting to External System ---")
        println("Sending: $data")
        println("--- Export Success ---")
    }

}
