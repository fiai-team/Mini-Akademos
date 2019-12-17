package uci.fiai.miniakd.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uci.fiai.miniakd.database.entities.Brigade

@Dao
interface BrigadeDao {
    @Query("SELECT * FROM TBrigade")
    fun getAll(): List<Brigade>

    @Insert
    fun insertAll(vararg brigades: Brigade)

    @Delete
    fun delete(brigade: Brigade)

    @Query("SELECT * FROM TBrigade")
    fun any(): List<Brigade>

}