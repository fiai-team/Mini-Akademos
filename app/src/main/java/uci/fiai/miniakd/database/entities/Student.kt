package uci.fiai.miniakd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TStudents")
data class Student(
    @PrimaryKey(autoGenerate = true) val Id: Int
)