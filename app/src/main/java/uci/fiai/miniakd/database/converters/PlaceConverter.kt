package uci.fiai.miniakd.database.converters

import androidx.room.TypeConverter
import uci.fiai.miniakd.database.enums.Places

class PlaceConverter {
    @TypeConverter
    fun placeToInt(places: Places) : Int {
        return places.ordinal
    }

    @TypeConverter
    fun intToPlace(value: Int) : Places {
        return Places.of(value)
    }
}