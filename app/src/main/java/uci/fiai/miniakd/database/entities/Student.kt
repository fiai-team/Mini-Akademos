package uci.fiai.miniakd.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.NO_ACTION

@Entity(tableName = "TStudents", foreignKeys = [ForeignKey(entity = Brigade::class, parentColumns = ["id"], childColumns = ["brigadeId"], onUpdate = CASCADE, onDelete = CASCADE)])
class Student() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var lastName: String = ""
    var brigadeId: Int = 0
    var isRepentant: Boolean = false

    constructor(name: String, lastName: String, brigadeId: Int, isRepentant: Boolean) : this() {
        this.name = name
        this.lastName = lastName
        this.brigadeId = brigadeId
        this.isRepentant = isRepentant
    }
}