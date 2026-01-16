package gz.dam.simondice

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "score_table") // Cambia el nombre de la tabla
data class GameScore(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ronda: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun getFechaFormateada(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    companion object {
        fun empty(): GameScore = GameScore(ronda = 0, timestamp = System.currentTimeMillis())
    }
}