package uci.fiai.miniakd.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uci.fiai.miniakd.database.entities.Student

@Dao
interface StudentsDao {
    @Query("SELECT * FROM TStudents")
    fun getAll(): List<Student>

    @Insert
    fun insertAll(vararg users: Student)

    @Delete
    fun delete(user: Student)

    @Query("SELECT * FROM TStudents WHERE brigadeName = :s")
    fun getAllByBrigade(s: String): List<Student>
}