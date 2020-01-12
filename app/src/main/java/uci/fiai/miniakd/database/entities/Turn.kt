package uci.fiai.miniakd.database.entities

import androidx.room.*
import uci.fiai.miniakd.database.converters.LocalTypeConverter
import uci.fiai.miniakd.database.converters.PlaceConverter
import uci.fiai.miniakd.database.converters.TypologyConverter
import uci.fiai.miniakd.database.enums.LocalType
import uci.fiai.miniakd.database.enums.Places
import uci.fiai.miniakd.database.enums.Typology
import uci.fiai.miniakd.extensions.empty

@Entity(tableName = "Turn", foreignKeys = [
    ForeignKey(entity = Subject::class, parentColumns = ["id"], childColumns = ["subjectId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
    ForeignKey(entity = Brigade::class, parentColumns = ["id"], childColumns = ["brigadeId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
])
@TypeConverters(PlaceConverter::class, LocalTypeConverter::class, TypologyConverter::class)
class Turn() {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var subjectId = 0
    var brigadeId = 0
    //TODO: Change this way to Date sql format
    var year = 0
    var month = 0
    var day = 0
    var timeTurn = 0
    var place = Places.Docent1
    var local = String.empty
    var localType = LocalType.Aula
    var typology = Typology.Conference
}

