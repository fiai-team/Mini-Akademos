package uci.fiai.miniakd.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TStudents")
class Student() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var lastName: String = ""
    var brigadeName: String = ""
    var isRepentant: Boolean = false

    constructor(name: String, lastName: String, brigadeName: String, isRepentant: Boolean) : this() {
        this.name = name
        this.lastName = lastName
        this.brigadeName = brigadeName
        this.isRepentant = isRepentant
    }
}