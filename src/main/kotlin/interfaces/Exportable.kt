package interfaces

interface Exportable {
    // This is the "contract": any class implementing this must provide a format
    fun export(): String
}