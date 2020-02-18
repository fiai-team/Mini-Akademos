package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.extensions.*
import java.util.*
import kotlin.collections.ArrayList

class LoadTurnsAsyncTask (private val listener: ViewModelListener) : AsyncTask<Calendar, Void, ArrayList<Turn>>() {

    override fun doInBackground(vararg params: Calendar?): ArrayList<Turn> {
        when (params.size) {
            0 -> {
                return MainDatabase.instance(listener.context).turns.getAll() as ArrayList
            }
            1 -> {
                val date = params[0]!!
                val context = listener.context
                val turns = MainDatabase.instance(context).turns.getAllByDate(date.getYear(), date.getMonth(), date.getDay())
                for (turn in turns) {
                    val subject = MainDatabase.instance(context).subjects.getById(turn.subjectId)
                    turn.subject = subject
                }

                return turns as ArrayList
            }
        }
        return MainDatabase.instance(listener.context).turns.getAll() as ArrayList
    }

    override fun onPostExecute(result: ArrayList<Turn>) {
        super.onPostExecute(result)

        listener.onTaskFinished(result, this.javaClass.name)
    }
}