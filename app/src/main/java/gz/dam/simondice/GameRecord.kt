package gz.dam.simondice


import java.text.SimpleDateFormat
import java.util.*

/**
 * Clase que representa un récord en memoria (sin persistencia)
 */
data class GameRecord(
    val ronda: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Obtiene la fecha formateada del récord.
     */
    fun getFechaFormateada(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    companion object {
        /**
         * Crea un Record vacío.
         */
        fun empty(): GameRecord = GameRecord(ronda = 0, timestamp = System.currentTimeMillis())
    }
}