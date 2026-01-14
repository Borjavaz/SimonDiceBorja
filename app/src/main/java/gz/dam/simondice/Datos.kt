package gz.dam.simondice

import androidx.compose.ui.graphics.Color

/**
 * Clase que almacena los datos del juego de forma simplificada
 * Actúa como un contenedor estático para datos compartidos
 */
object Datos {
    // Secuencias del juego
    var secuencia = mutableListOf<Int>()
    var secuenciaUsuario = mutableListOf<Int>()

    // Función de utilidad para generar números aleatorios
    fun generarColorAleatorio(): Int = (0..3).random()

    /**
     * Reinicia el juego al estado inicial
     */
    fun reiniciarJuego() {
        secuencia.clear()
        secuenciaUsuario.clear()
    }

    /**
     * Verifica si la secuencia del usuario es correcta hasta ahora
     */
    fun verificarSecuenciaParcial(): Boolean {
        if (secuenciaUsuario.isEmpty() || secuencia.isEmpty()) return false
        if (secuenciaUsuario.size > secuencia.size) return false

        for (i in secuenciaUsuario.indices) {
            if (secuenciaUsuario[i] != secuencia[i]) {
                return false
            }
        }
        return true
    }

    /**
     * Verifica si el usuario completó toda la secuencia correctamente
     */
    fun verificarSecuenciaCompleta(): Boolean {
        if (secuenciaUsuario.size != secuencia.size) return false
        return verificarSecuenciaParcial()
    }
}

/**
 * Enum con los colores del juego y sus propiedades
 */
enum class Colores(val colorInt: Int, val nombre: String, val tono: String) {
    ROJO(0, "ROJO", "Mi"),
    VERDE(1, "VERDE", "Do"),
    AZUL(2, "AZUL", "Sol"),
    AMARILLO(3, "AMARILLO", "Do'");

    companion object {
        /**
         * Obtiene un color por su valor entero
         */
        fun fromInt(value: Int): Colores? {
            return values().firstOrNull { it.colorInt == value }
        }

        /**
         * Obtiene el nombre del color por su valor entero
         */
        fun getNombre(value: Int): String {
            return fromInt(value)?.nombre ?: "DESCONOCIDO"
        }
    }
}

/**
 * Definición de colores para el tema del juego
 * Estos colores deben estar definidos en tu archivo de tema
 */
object SimonColors {
    // Colores claros (activos/brillantes)
    val RedLight = Color(0xFFE53935)      // Rojo brillante
    val GreenLight = Color(0xFF43A047)    // Verde brillante
    val BlueLight = Color(0xFF1E88E5)     // Azul brillante
    val YellowLight = Color(0xFFFDD835)   // Amarillo brillante

    // Colores oscuros (inactivos/apagados)
    val RedDark = Color(0xFFB71C1C)       // Rojo oscuro
    val GreenDark = Color(0xFF1B5E20)     // Verde oscuro
    val BlueDark = Color(0xFF0D47A1)      // Azul oscuro
    val YellowDark = Color(0xFFF57F17)    // Amarillo oscuro

    // Colores neutrales
    val Background = Color(0xFF121212)    // Fondo oscuro
    val TextPrimary = Color(0xFFFFFFFF)   // Texto principal
    val TextSecondary = Color(0xFFB0B0B0) // Texto secundario
}

// Función de extensión para obtener el color base (color CLARO/BRILLANTE, activo)
fun Colores.baseColor(): Color {
    return when (this) {
        Colores.ROJO -> SimonColors.RedLight
        Colores.VERDE -> SimonColors.GreenLight
        Colores.AZUL -> SimonColors.BlueLight
        Colores.AMARILLO -> SimonColors.YellowLight
    }
}

// Función de extensión para obtener el color oscurecido (color OSCURO/INACTIVO)
fun Colores.colorOscurecido(): Color {
    return when (this) {
        Colores.ROJO -> SimonColors.RedDark
        Colores.VERDE -> SimonColors.GreenDark
        Colores.AZUL -> SimonColors.BlueDark
        Colores.AMARILLO -> SimonColors.YellowDark
    }
}

/**
 * Función de utilidad para obtener el color correspondiente a un valor entero
 */
fun getColorFromInt(colorInt: Int, brillante: Boolean = true): Color {
    return when (colorInt) {
        0 -> if (brillante) SimonColors.RedLight else SimonColors.RedDark
        1 -> if (brillante) SimonColors.GreenLight else SimonColors.GreenDark
        2 -> if (brillante) SimonColors.BlueLight else SimonColors.BlueDark
        3 -> if (brillante) SimonColors.YellowLight else SimonColors.YellowDark
        else -> SimonColors.Background
    }
}