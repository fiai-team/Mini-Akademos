package uci.fiai.miniakd.database.converters

import androidx.room.TypeConverter
import uci.fiai.miniakd.database.enums.Assistance

class AssistanceConverter {
    @TypeConverter
    fun assistanceToInt(assistance: Assistance): Int {
        return assistance.ordinal
    }

    @TypeConverter
    fun intToAssistance(value: Int) : Assistance {
        return Assistance.of(value)
    }
}