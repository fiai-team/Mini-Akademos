package uci.fiai.miniakd.database.converters

import androidx.room.TypeConverter
import uci.fiai.miniakd.database.enums.LocalType

class LocalTypeConverter {
    @TypeConverter
    fun localTypeToInt(localType: LocalType): Int {
        return localType.ordinal
    }

    @TypeConverter
    fun intToLocalType(value: Int) : LocalType {
        return LocalType.of(value)
    }
}