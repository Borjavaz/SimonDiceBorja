package gz.dam.simondice


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel del juego Simon Dice sin Room Database
 */
class VM(application: Application) : ViewModel() {

    // Estados reactivos usando StateFlow para la UI
    private val _gameState = MutableStateFlow<GameState>(GameState.Inicio)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _ronda = MutableStateFlow(0)
    val ronda: StateFlow<Int> = _ronda.asStateFlow()

    // El record se mantiene solo en memoria
    private val _record = MutableStateFlow(Record.empty())
    val record: StateFlow<Record> = _record.asStateFlow()

    private val _text = MutableStateFlow("PRESIONA START")
    val text: StateFlow<String> = _text.asStateFlow()

    private val _colorActivo = MutableStateFlow(-1)
    val colorActivo: StateFlow<Int> = _colorActivo.asStateFlow()

    private val _botonesBrillantes = MutableStateFlow(false)
    val botonesBrillantes: StateFlow<Boolean> = _botonesBrillantes.asStateFlow()

    // Configuración de velocidades de la secuencia
    private val velocidadMostrarColor = 800L
    private val velocidadPausaEntreColores = 400L
    private val velocidadTiempoApagado = 200L
    private val velocidadPausaEntreRondas = 1200L

    // Listas internas para las secuencias de colores
    private val secuencia = mutableListOf<Int>()
    private val secuenciaUsuario = mutableListOf<Int>()

    // Estado para controlar si el juego está en progreso
    private val _juegoEnProgreso = MutableStateFlow(false)
    val juegoEnProgreso: StateFlow<Boolean> = _juegoEnProgreso.asStateFlow()

    init {
        // Inicializar el estado global de Datos
        Datos.reiniciarJuego()
    }

    /**
     * Genera un número aleatorio entre 0 y 3 (colores)
     */
    fun generaNumero(): Int = (0..3).random()

    /**
     * Comienza el juego desde el estado inicial o GameOver
     */
    fun comenzarJuego() {
        if (_gameState.value == GameState.Inicio || _gameState.value is GameState.GameOver) {
            reiniciarDatos()
            _gameState.value = GameState.Preparando
            _text.value = "PREPARADO..."
            _juegoEnProgreso.value = true

            viewModelScope.launch {
                delay(1000)
                comenzarNuevaRonda()
            }
        }
    }

    /**
     * Reinicia todos los datos del juego
     */
    private fun reiniciarDatos() {
        secuencia.clear()
        secuenciaUsuario.clear()
        _ronda.value = 0
        _colorActivo.value = -1
        _botonesBrillantes.value = false
        _juegoEnProgreso.value = true
        Datos.reiniciarJuego()
    }

    /**
     * Inicia una nueva ronda del juego
     */
    private fun comenzarNuevaRonda() {
        viewModelScope.launch {
            _gameState.value = GameState.MostrandoSecuencia
            _text.value = "OBSERVA LA SECUENCIA"
            _botonesBrillantes.value = false

            delay(500)

            // Añadir color aleatorio e incrementar ronda
            val nuevoColor = generaNumero()
            secuencia.add(nuevoColor)
            _ronda.value = secuencia.size

            mostrarSecuenciaCompleta()
        }
    }

    /**
     * Muestra la secuencia completa de colores al jugador
     */
    private suspend fun mostrarSecuenciaCompleta() {
        for ((index, colorInt) in secuencia.withIndex()) {
            _colorActivo.value = colorInt
            delay(velocidadMostrarColor)

            _colorActivo.value = -1
            delay(velocidadTiempoApagado)

            if (index < secuencia.size - 1) {
                delay(velocidadPausaEntreColores)
            }
        }
        delay(500)
        prepararTurnoJugador()
    }

    /**
     * Prepara el turno del jugador para repetir la secuencia
     */
    private fun prepararTurnoJugador() {
        secuenciaUsuario.clear()
        _gameState.value = GameState.EsperandoJugador
        _text.value = "TU TURNO - REPITE LA SECUENCIA"
        _botonesBrillantes.value = true
    }

    /**
     * Procesa el clic del usuario en un color
     */
    fun procesarClickUsuario(colorInt: Int) {
        if (_gameState.value != GameState.EsperandoJugador) return

        viewModelScope.launch {
            _gameState.value = GameState.ProcesandoInput
            _botonesBrillantes.value = false

            _colorActivo.value = colorInt
            delay(400)
            _colorActivo.value = -1

            secuenciaUsuario.add(colorInt)
            verificarSecuenciaUsuario()
        }
    }

    /**
     * Verifica si la secuencia del usuario es correcta
     */
    private fun verificarSecuenciaUsuario() {
        val indiceActual = secuenciaUsuario.size - 1

        // Verificar si el color clickeado es correcto
        if (secuenciaUsuario[indiceActual] != secuencia[indiceActual]) {
            gameOver()
            return
        }

        // Verificar si completó toda la secuencia
        if (secuenciaUsuario.size == secuencia.size) {
            secuenciaCorrecta()
        } else {
            _gameState.value = GameState.EsperandoJugador
            _text.value = "CONTINÚA... ${secuenciaUsuario.size}/${secuencia.size}"
            _botonesBrillantes.value = true
        }
    }

    /**
     * Ejecuta las acciones cuando la secuencia es correcta
     */
    private fun secuenciaCorrecta() {
        viewModelScope.launch {
            _gameState.value = GameState.SecuenciaCorrecta
            _text.value = "¡BIEN! SIGUIENTE RONDA"

            // Lógica de nuevo récord en memoria (sin persistencia)
            if (_ronda.value > _record.value.ronda) {
                val nuevoRecord = Record(ronda = _ronda.value)
                _record.value = nuevoRecord
            }

            efectoCelebracion()
            delay(velocidadPausaEntreRondas)
            comenzarNuevaRonda()
        }
    }

    /**
     * Efecto de celebración visual cuando se completa una ronda
     */
    private suspend fun efectoCelebracion() {
        repeat(2) {
            for (i in 0..3) {
                _colorActivo.value = i
                delay(150)
            }
            _colorActivo.value = -1
            delay(200)
        }
    }

    /**
     * Finaliza el juego cuando el usuario comete un error
     */
    private fun gameOver() {
        viewModelScope.launch {
            _gameState.value = GameState.GameOver(_ronda.value)
            _text.value = "GAME OVER - RONDA ${_ronda.value}"
            _botonesBrillantes.value = false
            _juegoEnProgreso.value = false

            efectoGameOver()

            delay(2000)
            _text.value = "RÉCORD: ${_record.value.ronda} - PRESIONA START"
        }
    }

    /**
     * Efecto visual cuando el juego termina
     */
    private suspend fun efectoGameOver() {
        repeat(3) {
            _colorActivo.value = 0
            delay(400)
            _colorActivo.value = -1
            delay(400)
        }
    }

    /**
     * Reinicia el juego manualmente desde cualquier estado
     */
    fun reiniciarJuego() {
        if (_gameState.value != GameState.Inicio) {
            viewModelScope.launch {
                _gameState.value = GameState.GameOver(_ronda.value)
                _text.value = "JUEGO REINICIADO"
                _botonesBrillantes.value = false
                _juegoEnProgreso.value = false

                efectoCelebracion()

                _text.value = "RÉCORD: ${_record.value.ronda} - PRESIONA START"
            }
        }
    }

    /**
     * Obtiene la secuencia actual del juego
     */
    fun getSecuenciaActual(): List<Int> = secuencia.toList()

    /**
     * Obtiene la secuencia del usuario actual
     */
    fun getSecuenciaUsuario(): List<Int> = secuenciaUsuario.toList()

    /**
     * Verifica si es el turno del jugador
     */
    fun esTurnoJugador(): Boolean = _gameState.value == GameState.EsperandoJugador
}