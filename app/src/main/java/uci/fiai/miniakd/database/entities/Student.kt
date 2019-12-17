package uci.fiai.miniakd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TStudents")
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