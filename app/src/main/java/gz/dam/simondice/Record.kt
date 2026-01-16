package gz.dam.simondice


import java.text.SimpleDateFormat
import java.util.*

/**
 * Data class que representa un record del juego
 * @param ronda Ronda más alta alcanzada
 * @param timestamp Fecha y hora en que se consiguió el record (en formato long de milisegundos)
 */
data class Record(
    val ronda: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Obtiene la fecha formateada del record
     */
    fun getFechaFormateada(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    companion object {
        /**
         * Crea un Record vacío (ronda 0, timestamp actual)
         */
        fun empty(): Record = Record(0, System.currentTimeMillis())
    }
}