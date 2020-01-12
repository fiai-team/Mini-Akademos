package uci.fiai.miniakd.database.dao

import androidx.room.*
import uci.fiai.miniakd.database.entities.Subject

@Dao
interface SubjectDao {

    @Insert
    fun insert(subject: Subject)

    @Update
    fun update(subject: Subject)

}