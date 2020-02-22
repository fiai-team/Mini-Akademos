package uci.fiai.miniakd.database.dao

import androidx.room.*
import uci.fiai.miniakd.database.entities.Turn

@Dao
interface TurnDao {
    @Insert
    fun insertAll(vararg brigades: Turn)

    @Query("SELECT * FROM Turn")
    fun getAll(): List<Turn>

    @Query("SELECT * FROM Turn WHERE day = :day AND month = :month AND year = :year")
    fun getAllByDate(year: Int, month: Int, day: Int): List<Turn>

    @Query("SELECT * FROM Turn WHERE id = :id LIMIT 1")
    fun findById(id: Int): Turn
}