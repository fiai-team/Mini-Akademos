package uci.fiai.miniakd.fragments.turns

import android.content.Context
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask
import uci.fiai.miniakd.tasks.LoadSubjectsAsyncTask
import uci.fiai.miniakd.tasks.LoadTurnsAsyncTask
import uci.fiai.miniakd.tasks.ViewModelListener
import java.util.*

class TurnsFragmentViewModel(context: Context) : ViewModelListener(context) {

    var turnForDateList = MutableLiveData<ArrayList<Turn>>()

    var selectedDate = MutableLiveData<Calendar>().apply {
        this.value = Calendar.getInstance()
    }
    var brigadesList = MutableLiveData<ArrayList<Brigade>>()
    var subjectsList = MutableLiveData<ArrayList<Subject>>()


    init {
        updateTurns()
        update()
    }

    override fun onTaskFinished(result: ArrayList<*>, taskName: String) {
        when (taskName) {
            LoadBrigadesAsyncTask::class.qualifiedName -> {
                brigadesList.apply {
                    this.value = result.filterIsInstance<Brigade>() as ArrayList<Brigade>
                }
            }
            LoadSubjectsAsyncTask::class.qualifiedName -> {
                subjectsList.apply {
                    this.value = result.filterIsInstance<Subject>() as ArrayList<Subject>
                }
            }
            LoadTurnsAsyncTask::class.qualifiedName -> {
                turnForDateList.apply {
                    this.value = result.filterIsInstance<Turn>() as ArrayList<Turn>
                }
            }
        }
    }

    fun update() {
        val loadBrigadesAsyncTask = LoadBrigadesAsyncTask(this)
        loadBrigadesAsyncTask.execute()
        val loadSubjectsAsyncTask = LoadSubjectsAsyncTask(this)
        loadSubjectsAsyncTask.execute()
    }

    fun updateTurns() {
        val loadTurnsAsyncTask = LoadTurnsAsyncTask(this)
        loadTurnsAsyncTask.execute(selectedDate.value)
    }

    fun insertTurn(turn: Turn) {
        Thread {
            MainDatabase.instance(context).turns.insertAll(turn)
            updateTurns()
        }.start()
    }


}
