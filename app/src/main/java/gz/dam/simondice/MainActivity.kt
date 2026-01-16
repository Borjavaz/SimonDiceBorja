package gz.dam.simondice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import gz.dam.simondice.ui.theme.SimonDiceTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SimonDiceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // CAMBIO: Pasar el ViewModelFactory al composable
                    val viewModel: VM = viewModel(
                        factory = VMFactory(application)
                    )
                    SimonDiceUI(viewModel = viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}