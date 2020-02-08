package uci.fiai.miniakd.fragments.turns

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask
import uci.fiai.miniakd.tasks.LoadSubjectsAsyncTask
import uci.fiai.miniakd.tasks.LoadTurnsAsyncTask
import java.util.*

class TurnsFragmentViewModel(application: Application) : AndroidViewModelListener(application){

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

    override fun context(): Context {
        return getApplication()
    }

    override fun onTaskFinished(result: List<Any>, taskName: String) {
        when (taskName) {
            LoadBrigadesAsyncTask::class.qualifiedName -> {
                brigadesList.apply {
                    this.value = result as ArrayList<Brigade>
                }
            }
            LoadSubjectsAsyncTask::class.qualifiedName -> {
                subjectsList.apply {
                    this.value = result as ArrayList<Subject>
                }
            }
            LoadTurnsAsyncTask::class.qualifiedName -> {
                turnForDateList.apply {
                    this.value = result as ArrayList<Turn>
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
            MainDatabase.instance(context()).turns.insertAll(turn)
            updateTurns()
        }.start()
    }


}
