package uci.fiai.miniakd.database.dao

import androidx.room.*
import uci.fiai.miniakd.database.entities.Student

@Dao
interface StudentsDao {
    @Query("SELECT * FROM TStudents")
    fun getAll(): List<Student>

    @Insert
    fun insertAll(vararg users: Student)

    @Delete
    fun delete(user: Student)

    @Query("SELECT * FROM TStudents WHERE brigadeId = :s")
    fun getAllByBrigade(s: Int): List<Student>

    @Update
    fun update(student: Student)
}