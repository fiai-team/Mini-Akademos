package uci.fiai.miniakd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uci.fiai.miniakd.database.converters.AssistanceConverter
import uci.fiai.miniakd.database.converters.CalendarConverter
import uci.fiai.miniakd.database.enums.Assistance
import java.util.*

@Entity(tableName = "TurnAssistance")
@TypeConverters(AssistanceConverter::class, CalendarConverter::class)
class TurnAssistance() {
    var year: Int = 0
    var month: Int = 0
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var turnId = 0
    var studentId = 0
    var status: Assistance = Assistance.NONE
    var brigadeId: Int = 0
    var day: Int = 0
}