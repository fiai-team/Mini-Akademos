package uci.fiai.miniakd.fragments.turns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Turn

class TurnsFragmentViewModel : ViewModel() {

    var turnForDate = MutableLiveData<ArrayList<Turn>>().apply {
        this.value = ArrayList(0)
    }

    var turnForDateList : LiveData<ArrayList<Turn>> = turnForDate
}
