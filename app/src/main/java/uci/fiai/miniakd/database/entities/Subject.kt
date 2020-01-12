package uci.fiai.miniakd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import uci.fiai.miniakd.extensions.*


@Entity(tableName = "Subject")
class Subject() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = String.empty
    var hoursCount: Int = 0
    var period = 0
    var doneHoursCount: Int = 0
    var hasPartialsExams: Boolean = true
    var hasFinalExam: Boolean = true
}