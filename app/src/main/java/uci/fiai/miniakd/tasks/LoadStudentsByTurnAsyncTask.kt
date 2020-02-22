package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.database.entities.Turn

class LoadStudentsByTurnAsyncTask(private val turn: Turn, private val listener: ViewModelListener) : AsyncTask<Void, Void, ArrayList<Student>>() {
    override fun doInBackground(vararg params: Void?): ArrayList<Student> {
        return MainDatabase.instance(listener.context).students.getAllByBrigade(turn.brigadeId) as ArrayList
    }

    override fun onPostExecute(result: ArrayList<Student>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}