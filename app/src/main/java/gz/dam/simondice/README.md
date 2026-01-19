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

### 4. Actualizar el `VM` para usar MongoDB
- Modificaré el **VM (ViewModel)** para que utilice la nueva clase `MongoDB` para guardar y cargar el récord.
- Esto implicará añadir nuevas funciones al VM para manejar la nueva fuente de datos.

### 5. Modificar la UI para mostrar la nueva fuente de récords
- Ajustaré la interfaz de usuario para permitir al usuario seleccionar **MongoDB** como fuente de datos.
- Mostraré el récord obtenido desde esta nueva fuente.

---

Empezaré con el primer punto para investigar la estructura actual.


username: admin
password: Z8vTYuwSBBghPovt

