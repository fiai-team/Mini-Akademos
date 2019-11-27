package uci.fiai.miniakd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uci.fiai.miniakd.database.dao.StudentsDao
import uci.fiai.miniakd.database.entities.Student

@Database(entities = [Student::class], version = 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun studentsDao(): StudentsDao

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