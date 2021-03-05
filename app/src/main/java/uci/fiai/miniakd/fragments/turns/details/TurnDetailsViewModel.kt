package uci.fiai.miniakd.fragments.turns.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.LoadStudentsByTurnAsyncTask
import uci.fiai.miniakd.tasks.ViewModelListener

class TurnDetailsViewModel(context: Context, val turnId: Int) : ViewModelListener(context) {

    val studentsList = MutableLiveData<ArrayList<Student>>()
    private val currentThread = Thread.currentThread()

    init {
        update()
    }

    override fun onTaskFinished(result: ArrayList<*>, taskName: String) {
        when (taskName) {
            LoadStudentsByTurnAsyncTask::class.qualifiedName -> {
                studentsList.apply {
                    value = result.filterIsInstance<Student>() as ArrayList
                }
            }
        }
    }

    fun update() {
        Thread {
            /*val db =MainDatabase.instance(context)
            val turn = db.turns.findById(turnId)
            LoadStudentsByTurnAsyncTask(turn, this).execute()*/
        }.start()

    }
}