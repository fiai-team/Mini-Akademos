package uci.fiai.miniakd.database.converters

import androidx.room.TypeConverter
import uci.fiai.miniakd.database.enums.Typology

class TypologyConverter {

    @TypeConverter
    fun typologyToInt(typology: Typology) : Int {
        return typology.ordinal
    }

    @TypeConverter
    fun intToTypology(value: Int) : Typology {
        return Typology.of(value)
    }
}