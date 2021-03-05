package uci.fiai.miniakd.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uci.fiai.miniakd.database.entities.TurnAssistance

@Dao
abstract class TurnAssistanceDao {

    @Insert
    abstract fun create(turnAssistance: TurnAssistance)

}
