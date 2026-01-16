package gz.dam.simondice

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VMFactory(
    private val application: Application,
    private val recordDao: ScoreDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VM::class.java)) {
            return VM(application, recordDao) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}