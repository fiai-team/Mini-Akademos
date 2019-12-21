package uci.fiai.miniakd.database.dao

import androidx.room.*
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student

@Dao
interface BrigadeDao {

    @Query("SELECT * FROM TBrigade")
    fun getAll(): List<Brigade>

    @Query("SELECT * FROM TBrigade WHERE id = :name LIMIT 1")
    fun getByName(name: Int): Brigade

    @Query("SELECT * FROM TStudents WHERE brigadeId = :id")
    fun getStudentsByBrigadeId(id: Int): List<Student>

    @Query("SELECT * FROM TBrigade WHERE id = :id LIMIT 1")
    fun getBrigadeById(id: Int): Brigade

    @Insert
    fun insertAll(vararg brigades: Brigade)

    @Delete
    fun delete(brigade: Brigade)

    @Query("SELECT * FROM TBrigade")
    fun any(): List<Brigade>

    @Update
    fun update(brigade: Brigade)

}