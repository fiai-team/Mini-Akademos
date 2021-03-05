package uci.fiai.miniakd.database.converters

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}