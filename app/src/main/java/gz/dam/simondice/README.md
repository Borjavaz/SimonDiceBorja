# Planificación para integrar MongoDB en tu aplicación **Simon Dice**

## Plan de Tareas

### 1. Investigar la persistencia de datos actual
- Analizaré las implementaciones existentes de **SharedPreferences** y **SQLite** para entender cómo se  
  guardan y recuperan los datos actualmente.
- Esto me ayudará a mantener una estructura consistente.

### 2. Integrar el driver de MongoDB para Kotlin
- Añadiré la dependencia del **driver oficial de MongoDB para Kotlin** en el archivo `build.gradle` del  
  proyecto.

### 3. Implementar la clase `MongoDB`
- Crearé una nueva clase que se encargará de todas las interacciones con la base de datos MongoDB.
- Esta clase incluirá métodos para:
    - Conectar con la base de datos
    - Insertar nuevos récords
    - Obtener la puntuación más alta

---

# LO QUE IMPLEMENTAMOS CON MONGODB

## 1. CREAMOS UN "PUENTE" HACIA LA NUBE 🌉

Creamos una nueva clase **`MongoDBManager`** que actúa como intermediario entre la app y la base de datos en internet.

Su trabajo es:
- Conectar a **MongoDB Atlas** (servicio en la nube)
- Enviar los récords al servidor
- Traer récords guardados anteriormente
- Manejar errores si no hay conexión a internet

---

## 2. RENOMBRAMOS Y MEJORAMOS EL MODELO 🔄

Cambiamos **`Record`** por **`GameRecord`** para ser más específicos y prepararlo para guardarse en MongoDB.

---

## 3. MODIFICAMOS EL "CEREBRO" DE LA APP 🧠

El **ViewModel (`VM.kt`)** ahora hace **dos cosas** en lugar de una.

### ANTES
```kotlin
// Solo guardaba en memoria
if (nuevoRecord) {
    _record.value = nuevoRecord
}
```

### AHORA

```kotlin
// Guarda en memoria Y en la nube
if (nuevoRecord) {
    _record.value = nuevoRecord
    mongoDBManager.guardarRecord(nuevoRecord) // ¡Nueva línea!
}
```
Además, cuando la app se abre:

- Primero mira si hay un récord guardado en MongoDB
- Si lo hay, lo muestra
- Si no hay internet, usa el último récord local

## 4. AÑADIMOS NUEVAS CAPACIDADES

- **Persistencia real**: los récords sobreviven a reinicios de la app
- **Posible ranking global**: podemos ver récords de otros jugadores
- **Sincronización**: mismo récord en varios dispositivos
- **Backup automático**: MongoDB guarda copias de seguridad


---

CODIGO:

## MongoDBManager.kt

"El intermediario entre tu teléfono y la nube"

### 1. LAS CREDENCIALES - La dirección del servidor

```kotlin
private val connectionString = "mongodb://admin:Z8vTYuwSBBghPovt@borja-shard-00-00..."
```
Esto se obtiene del MongoDB Atlas


#### inicializarConexion() - Abrir el canal

```kotlin
suspend fun inicializarConexion(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            // Configurar los ajustes de conexión
            val settings = MongoClientSettings.builder()
                .applyConnectionString(ConnectionString(connectionString))
                .applyToClusterSettings { builder ->
                    // TIME OUT: Si no conecta en 5 segundos, cancela
                    builder.serverSelectionTimeout(5000, java.util.concurrent.TimeUnit.MILLISECONDS)
                }
                .build()
            
            // 1. Crear el cliente (abrir la puerta)
            mongoClient = MongoClient.create(settings)
            
            // 2. Entrar al edificio (base de datos)
            database = mongoClient?.getDatabase(databaseName)
            
            // 3. Abrir el archivero específico (colección)
            collection = database?.getCollection(collectionName, GameRecord::class.java)
            
            conexionInicializada = true
            true  // ¡Éxito!
        } catch (e: Exception) {
            Log.e("MONGODB", "Error de conexión: ${e.message}")
            false  // Error
        }
    }
}

```

#### guardarRecord() - Enviar datos a la nube

```kotlin
suspend fun guardarRecord(record: GameRecord): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            // Paso 1: ¿Ya estamos conectados?
            if (!conexionInicializada) {
                inicializarConexion()  // Si no, conecta primero
            }

            // Paso 2: ¡ENVIAR!
            collection?.insertOne(record)
            true  // Confirmación de éxito
        } catch (e: Exception) {
            e.printStackTrace()
            false  // Algo falló
        }
    }
}

```

#### obtenerMejorRecord() - Buscar el campeón

```kotlin
suspend fun obtenerMejorRecord(): GameRecord? {
    return withContext(Dispatchers.IO) {
        try {
            if (!conexionInicializada) {
                inicializarConexion()
            }

            collection?.find()
                ?.sort(Sorts.descending("ronda"))  // Ordenar de MAYOR a menor
                ?.firstOrNull()                     // Tomar solo el PRIMERO
        } catch (e: Exception) {
            e.printStackTrace()
            null  // Si falla, devuelve nada
        }
    }
}

```
---

## VM.kt

### La nueva variable: El mensajero contratado

```kotlin
private val mongoDBManager = MongoDBManager(application)
```

Antes: Solo manejaba datos locales
Ahora: Tiene un empleado que habla con internet

### El init modificado - Cargar al iniciar
   
```kotlin
init {
    // Inicializar el estado global de Datos
    Datos.reiniciarJuego()

    // NUEVO: Cargar el récord de MongoDB al iniciar
    viewModelScope.launch {
        cargarRecordDesdeMongoDB()
    }
}
```

### cargarRecordDesdeMongoDB() - El chequeo inicial

```kotlin
private suspend fun cargarRecordDesdeMongoDB() {
    // Paso 1: Pedir al mensajero que busque
    val recordMongoDB = mongoDBManager.obtenerMejorRecord()
    
    // Paso 2: Si encontró algo...
    recordMongoDB?.let { recordDesdeBD ->
        // Paso 3: ¿Es mejor que lo que tengo?
        if (recordDesdeBD.ronda > _record.value.ronda) {
            // Paso 4: ¡Actualizar!
            _record.value = recordDesdeBD
        }
    }
}
```

### guardarRecordEnMongoDB() - El guardado automático

```kotlin
private suspend fun guardarRecordEnMongoDB() {
    mongoDBManager.guardarRecord(_record.value)
}
```


