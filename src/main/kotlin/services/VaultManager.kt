package services

import models.VaultItem
import models.PasswordItem
import models.VaultLockedException

class VaultManager(private val masterPassword: String) {
    private val items = mutableListOf<VaultItem>()
    private var isUnlocked = false

    fun unlock(password: String): Boolean {
        if (password != masterPassword) {
            System.err.println("Access Denied: Incorrect Master Password.")
            return false
        }

        isUnlocked = true
        println("Vault Unlocked successfully.")
        return true
    }

    fun revealSensitiveData(id: Int) {
        // Guard Clause: Check if vault is unlocked
        if (!isUnlocked) {
            throw VaultLockedException("Cannot access data: Vault is currently locked.")
        }

        val item = items.find { it.id == id }

        // Guard Clause: Item existence
        if (item == null) {
            println("Error: Item not found.")
            return
        }

        when (item) {
            is PasswordItem -> println("Secret Password: ${item.revealPassword()}")
            else -> println("Content: ${item.getDisplayContent()}")
        }
    }

    fun addItem(item: VaultItem) {
        // Guard Clause: Duplicate titles are not allowed
        if (items.any { it.title.equals(item.title, ignoreCase = true) }) {
            System.err.println("Error: A secret with title '${item.title}' already exists.")
            return
        }

        items.add(item)
        println("Success: Item '${item.title}' added to vault.")
    }

    fun getAllItems(): List<VaultItem> {
        return items
    }

    fun findItemById(id: Int): VaultItem? {
        return items.find { it.id == id }
    }
}