package uci.fiai.miniakd.database.dao

import androidx.room.*
import uci.fiai.miniakd.database.entities.Subject

@Dao
interface SubjectDao {

    @Insert
    fun insert(subject: Subject)

    @Update
    fun update(subject: Subject)

    @Query("SELECT * FROM Subject")
    fun getAll() : List<Subject>

    @Query("SELECT * FROM Subject WHERE id = :subjectId LIMIT 1")
    fun getById(subjectId: Int) : Subject

    @Delete
    fun delete(subject: Subject)



}