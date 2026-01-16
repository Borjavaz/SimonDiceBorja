package gz.dam.simondice

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameScore::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}