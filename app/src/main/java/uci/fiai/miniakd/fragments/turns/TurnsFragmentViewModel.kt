package uci.fiai.miniakd.fragments.turns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Turn
import java.util.*
import kotlin.collections.ArrayList

class TurnsFragmentViewModel : ViewModel() {

    var turnForDate = MutableLiveData<ArrayList<Turn>>().apply {
        this.value = ArrayList(0)
    }

    var selectedDate = MutableLiveData<Calendar>().apply {
        this.value = Calendar.getInstance(Locale.getDefault())
    }

    var turnForDateList : LiveData<ArrayList<Turn>> = turnForDate

}
