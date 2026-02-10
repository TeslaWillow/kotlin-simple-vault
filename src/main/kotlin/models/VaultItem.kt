package models

import interfaces.Exportable

sealed class VaultItem(val id: Int, val title: String) : Exportable {
    abstract fun getDisplayContent(): String
}

class PasswordItem(
    id: Int,
    title: String,
    private val password: String
) : VaultItem(id, title) {
    override fun getDisplayContent(): String= "Password: " + "*".repeat(password.length)

    override fun export(): String = "FORMAT:ENCRYPTED | ID:$id | TITLE:$title | HASH:${password.hashCode()}"

    fun revealPassword(): String = password
}

class NoteItem(
    id: Int,
    title: String,
    val content: String
) : VaultItem(id, title) {
    override fun getDisplayContent(): String = "Note: $content"

    override fun export(): String = "FORMAT:TEXT | ID:$id | TITLE:$title | CONTENT:$content"
}


