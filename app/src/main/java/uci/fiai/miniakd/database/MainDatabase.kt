package uci.fiai.miniakd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uci.fiai.miniakd.database.dao.BrigadeDao
import uci.fiai.miniakd.database.dao.StudentsDao
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student

@Database(entities = [Student::class, Brigade::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun students(): StudentsDao

    abstract fun brigades(): BrigadeDao
    fun removeStudent(context: Context, student: Student) {
        TODO("aasdasda")
    }

    fun saveStudent(context: Context, student: Student) {

    }

    companion object {
        private const val DATABASE_NAME = "MainDatabase"
        private var instance: MainDatabase? = null
        fun instance(context: Context): MainDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME).build()
            }
            return instance as MainDatabase
        }
    }
}