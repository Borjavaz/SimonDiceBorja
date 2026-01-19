// [file name]: MongoDBManager.kt
package gz.dam.simondice

import android.content.Context
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext

/**
 * Clase para manejar todas las operaciones con MongoDB
 */
class MongoDBManager(private val context: Context) {

    // Configuración - REEMPLAZA CON TU CONNECTION STRING
    private val connectionString = "mongodb://admin:Z8vTYuwSBBghPovt@borja-shard-00-00.w4natan.mongodb.net:27017,borja-shard-00-01.w4natan.mongodb.net:27017,borja-shard-00-02.w4natan.mongodb.net:27017/test?ssl=true&replicaSet=atlas-shard-0&authSource=admin&retryWrites=true&w=majority"
    private val databaseName = "simon_dice"
    private val collectionName = "records"

    private var mongoClient: MongoClient? = null
    private var database: MongoDatabase? = null
    private var collection: MongoCollection<GameRecord>? = null
    private var conexionInicializada = false

    /**
     * Inicializa la conexión con MongoDB
     */
    private suspend fun inicializarConexion(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val settings = MongoClientSettings.builder()
                    .applyConnectionString(ConnectionString(connectionString))
                    // Esta línea es vital para que no se quede colgado si hay errores de DNS
                    .applyToClusterSettings { builder ->
                        builder.serverSelectionTimeout(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                    }
                    .build()

                mongoClient = MongoClient.create(settings)
                database = mongoClient?.getDatabase(databaseName)
                collection = database?.getCollection(collectionName, GameRecord::class.java)
                conexionInicializada = true
                true
            } catch (e: Exception) {
                android.util.Log.e("MONGODB", "Error de conexión: ${e.message}")
                false
            }
        }
    }

    /**
     * Guarda un récord en MongoDB
     */
    suspend fun guardarRecord(record: GameRecord): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (!conexionInicializada) {
                    inicializarConexion()
                }

                collection?.insertOne(record)
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    /**
     * Obtiene el mejor récord (más alta ronda) de MongoDB
     */
    suspend fun obtenerMejorRecord(): GameRecord? {
        return withContext(Dispatchers.IO) {
            try {
                if (!conexionInicializada) {
                    inicializarConexion()
                }

                collection?.find()
                    ?.sort(Sorts.descending("ronda"))
                    ?.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /**
     * Obtiene el top de récords desde MongoDB
     */
    suspend fun obtenerTopRecords(limit: Int = 10): List<GameRecord> {
        return withContext(Dispatchers.IO) {
            try {
                if (!conexionInicializada) {
                    inicializarConexion()
                }

                collection?.find()
                    ?.sort(Sorts.descending("ronda"))
                    ?.limit(limit)
                    ?.toList() ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    /**
     * Cierra la conexión con MongoDB
     */
    fun cerrarConexion() {
        try {
            mongoClient?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}