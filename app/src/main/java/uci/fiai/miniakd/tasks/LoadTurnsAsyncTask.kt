package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.extensions.*
import java.util.*

class LoadTurnsAsyncTask (private val listener: AndroidViewModelListener) : AsyncTask<Calendar, Void, List<Turn>>() {

    override fun doInBackground(vararg params: Calendar?): List<Turn> {
        when (params.size) {
            0 -> {
                return MainDatabase.instance(listener.context()).turns.getAll()
            }
            1 -> {
                val date = params[0]!!
                val context = listener.context()
                val turns = MainDatabase.instance(context).turns.getAllByDate(date.getYear(), date.getMonth(), date.getDay())
                for (turn in turns) {
                    val subject = MainDatabase.instance(context).subjects.getById(turn.subjectId)
                    turn.subject = subject
                }

                return turns
            }
        }
        return MainDatabase.instance(listener.context()).turns.getAll()
    }

    override fun onPostExecute(result: List<Turn>) {
        super.onPostExecute(result)

        listener.onTaskFinished(result, this.javaClass.name)
    }
}