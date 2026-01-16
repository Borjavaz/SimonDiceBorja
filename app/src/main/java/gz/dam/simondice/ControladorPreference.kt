package gz.dam.simondice


import android.content.Context
import androidx.core.content.edit

object ControladorPreference {
    // definimos el nombre del fichero de preferencias
    private const val PREFS_NAME = "simon_dice_record"
    // definimos las claves para guardar los datos
    private const val KEY_RECORD_RONDA = "record_ronda"
    private const val KEY_RECORD_TIMESTAMP = "record_timestamp"

    /**
     * Guarda un nuevo record en las preferencias compartidas.
     * @param context Contexto de la aplicación.
     * @param record El record a guardar.
     */
    fun guardarRecord(context: Context, record: Record) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putInt(KEY_RECORD_RONDA, record.ronda)
            putLong(KEY_RECORD_TIMESTAMP, record.timestamp)
        }
    }

    /**
     * Obtiene el record guardado en las preferencias compartidas.
     * @param context Contexto de la aplicación.
     * @return El record guardado, o un record vacío si no existe.
     */
    fun obtenerRecord(context: Context): Record {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val ronda = sharedPreferences.getInt(KEY_RECORD_RONDA, 0)
        val timestamp = sharedPreferences.getLong(KEY_RECORD_TIMESTAMP, 0L)

        // Si timestamp es 0, significa que no hay record guardado
        return if (timestamp == 0L) {
            Record.empty()
        } else {
            Record(ronda, timestamp)
        }
    }

    /**
     * Limpia el record guardado (establece ronda a 0)
     * @param context Contexto de la aplicación.
     */
    fun limpiarRecord(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            remove(KEY_RECORD_RONDA)
            remove(KEY_RECORD_TIMESTAMP)
        }
    }
}