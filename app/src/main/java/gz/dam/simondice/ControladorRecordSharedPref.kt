package gz.dam.simondice


import android.content.Context
import androidx.core.content.edit

/**
 * Implementación del ControladorRecord usando SharedPreferences
 * @param context Contexto de la aplicación
 */
class ControladorRecordSharedPref(private val context: Context) : ControladorRecord {

    companion object {
        // definimos el nombre del fichero de preferencias
        private const val PREFS_NAME = "simon_dice_record"
        // definimos las claves para guardar los datos
        private const val KEY_RECORD_RONDA = "record_ronda"
        private const val KEY_RECORD_TIMESTAMP = "record_timestamp"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun guardarRecord(record: Record) {
        sharedPreferences.edit {
            putInt(KEY_RECORD_RONDA, record.ronda)
            putLong(KEY_RECORD_TIMESTAMP, record.timestamp)
        }
    }

    override fun obtenerRecord(): Record {
        val ronda = sharedPreferences.getInt(KEY_RECORD_RONDA, 0)
        val timestamp = sharedPreferences.getLong(KEY_RECORD_TIMESTAMP, 0L)

        // Si timestamp es 0, significa que no hay record guardado
        return if (timestamp == 0L) {
            Record.empty()
        } else {
            Record(ronda, timestamp)
        }
    }

    override fun limpiarRecord() {
        sharedPreferences.edit {
            remove(KEY_RECORD_RONDA)
            remove(KEY_RECORD_TIMESTAMP)
        }
    }

    override fun esNuevoRecord(nuevoScore: Int): Boolean {
        val recordActual = obtenerRecord()
        return nuevoScore > recordActual.ronda
    }
}