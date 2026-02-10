import models.*
import services.*

fun main() {
    val vault = VaultManager("Admin123")
    val exporter = ExportService()

    // 1. Initial Setup
    vault.addItem(PasswordItem(1, "Work Email", "P@ssword!"))
    vault.addItem(NoteItem(2, "Gym Locker", "Code is 4455"))

    println("--- Simple Vault CLI ---")

    // 2. Attempting to export while locked (Triggers Guard Clause/Exception)
    try {
        val secret = vault.findItemById(1)

        // Guard Clause: Item existence
        if (secret == null) {
            println("Item not found.")
            return
        }

        // This will fail if the vault is locked
        // vault.revealSensitiveData(1)
        // exporter.executeExport(secret)

    } catch (e: VaultLockedException) {
        System.err.println("Access Blocked: ${e.message}")
    }

    // 3. Unlocking and Exporting
    println("\nSystem: Unlocking Vault...")
    if (vault.unlock("Admin123")) {
        val secret = vault.findItemById(1)
        if (secret != null) {
            vault.revealSensitiveData(1)
            exporter.executeExport(secret) // Polimorfism
        }
    }
}

fun requestSecretAccess(vault: VaultManager) {
    print("\nEnter the ID of the secret: ")
    val input = readLine() ?: ""

    // Try-Catch block to handle conversion errors
    val id = try {
        input.toInt() // If this fails, it jumps to 'catch'
    } catch (e: NumberFormatException) {
        // Guard Clause: Handle the error and exit the function early
        System.err.println("Error: '$input' is not a valid numeric ID.")
        return
    }

    // If we reach here, 'id' is a valid Int
    vault.revealSensitiveData(id)
}