package gz.dam.simondice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Query("SELECT * FROM score_table ORDER BY ronda DESC LIMIT 1")
    suspend fun getHighScore(): GameScore?

    @Insert
    suspend fun insert(record: GameScore)

    @Query("DELETE FROM score_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM score_table ORDER BY ronda DESC LIMIT 1")
    fun getHighScoreFlow(): Flow<GameScore?>
}