package uci.fiai.miniakd.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import uci.fiai.miniakd.database.dao.*
import uci.fiai.miniakd.database.entities.*

@Database(entities = [Student::class, Brigade::class, Subject::class, Turn::class, TurnAssistance::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract val students : StudentsDao

    abstract val brigades: BrigadeDao

    abstract val turns : TurnDao

    abstract val subjects: SubjectDao

    abstract val turnAssistance: TurnAssistanceDao

    companion object {
        private const val DATABASE_NAME = "MainDatabase"
        private var instance: MainDatabase? = null

        fun instance(context: Context): MainDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, MainDatabase::class.java, DATABASE_NAME).build()
            }
            return instance as MainDatabase
        }
    }
}