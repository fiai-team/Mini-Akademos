package uci.fiai.miniakd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TBrigade")
class Brigade() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var studentsBrigade: Int = 0

    constructor(name: String) : this() {
        this.name = name
    }
}