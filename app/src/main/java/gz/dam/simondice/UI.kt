package gz.dam.simondice

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SimonDiceUI(viewModel: VM) {
    // Estados del juego
    val gameState by viewModel.gameState.collectAsState()
    val ronda by viewModel.ronda.collectAsState()
    val record by viewModel.record.collectAsState()
    val text by viewModel.text.collectAsState()
    val colorActivo by viewModel.colorActivo.collectAsState()
    val botonesBrillantes by viewModel.botonesBrillantes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HeaderInfo(ronda, record, text, gameState)
        BotonesColores(viewModel, colorActivo, botonesBrillantes, gameState)
        BotonControl(viewModel, gameState)
    }
}

@Composable
fun HeaderInfo(
    ronda: Int,
    record: GameRecord,
    text: String,
    gameState: GameState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "SIMÓN DICE",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InfoBox("RONDA", ronda.toString())
            InfoBox("RÉCORD", record.ronda.toString())
            InfoBox("ESTADO", when (gameState) {
                is GameState.Inicio -> "INICIO"
                is GameState.Preparando -> "PREPARADO"
                is GameState.MostrandoSecuencia -> "OBSERVA"
                is GameState.EsperandoJugador -> "TU TURNO"
                is GameState.ProcesandoInput -> "PROCESANDO"
                is GameState.SecuenciaCorrecta -> "¡BIEN!"
                is GameState.GameOver -> "GAME OVER"
            })
        }

        // Mostrar la fecha del record solo si hay un record guardado
        if (record.ronda > 0) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Conseguido el: ${record.getFechaFormateada()}",
                fontSize = 12.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun InfoBox(titulo: String, valor: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = titulo, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .border(2.dp, Color.DarkGray)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(
                text = valor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BotonesColores(
    viewModel: VM,
    colorActivo: Int,
    botonesBrillantes: Boolean,
    gameState: GameState
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            BotonColor(
                viewModel = viewModel,
                color = Colores.ROJO,
                colorActivo = colorActivo,
                enabled = botonesBrillantes,
                gameState = gameState
            )
            Spacer(modifier = Modifier.width(20.dp))
            BotonColor(
                viewModel = viewModel,
                color = Colores.VERDE,
                colorActivo = colorActivo,
                enabled = botonesBrillantes,
                gameState = gameState
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            BotonColor(
                viewModel = viewModel,
                color = Colores.AMARILLO,
                colorActivo = colorActivo,
                enabled = botonesBrillantes,
                gameState = gameState
            )
            Spacer(modifier = Modifier.width(20.dp))
            BotonColor(
                viewModel = viewModel,
                color = Colores.AZUL,
                colorActivo = colorActivo,
                enabled = botonesBrillantes,
                gameState = gameState
            )
        }
    }
}

@Composable
fun BotonColor(
    viewModel: VM,
    color: Colores,
    colorActivo: Int,
    enabled: Boolean,
    gameState: GameState
) {
    val estaActivo = colorActivo == color.colorInt

    val colorBoton = when {
        estaActivo -> color.baseColor()
        enabled -> color.baseColor()
        else -> color.colorOscurecido()
    }

    Button(
        onClick = { viewModel.procesarClickUsuario(color.colorInt) },
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorBoton,
            disabledContainerColor = colorBoton
        ),
        modifier = Modifier
            .size(140.dp)
            .border(4.dp, Color.Black, CircleShape)
    ) {
        // Botón sin contenido adicional
    }
}

@Composable
fun BotonControl(viewModel: VM, gameState: GameState) {
    val textoBoton = when (gameState) {
        is GameState.Inicio, is GameState.GameOver -> "START"
        else -> "RESTART"
    }

    Button(
        onClick = {
            when (gameState) {
                is GameState.Inicio, is GameState.GameOver -> viewModel.comenzarJuego()
                else -> viewModel.reiniciarJuego()
            }
        },
        modifier = Modifier
            .width(200.dp)
            .height(60.dp)
    ) {
        Text(
            text = textoBoton,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}