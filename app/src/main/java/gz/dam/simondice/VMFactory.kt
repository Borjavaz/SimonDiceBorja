package gz.dam.simondice


import android.app.Application
import androidx.lifecycle.ViewModelProvider

/**
 * Factory para crear el ViewModel con contexto de aplicación
 */
class VMFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VM(application) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}