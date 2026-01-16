package gz.dam.simondice


/**
 * Interfaz para el controlador de records del juego
 * Define el contrato para guardar y recuperar records
 */
interface ControladorRecord {

    //Guarda un nuevo record
    fun guardarRecord(record: Record)


    //Obtiene el record guardado
    fun obtenerRecord(): Record


    //Limpia el record guardado

    fun limpiarRecord()

    //Verifica si un score es un nuevo record
    fun esNuevoRecord(nuevoScore: Int): Boolean
}