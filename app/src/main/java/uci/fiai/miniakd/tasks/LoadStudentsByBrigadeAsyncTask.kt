package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Student

class LoadStudentsByBrigadeAsyncTask(private val listener: ViewModelListener) : AsyncTask<Int, Void, ArrayList<Student>>() {

    override fun doInBackground(vararg params: Int?): ArrayList<Student>? {
        return when {
            params.isEmpty() -> MainDatabase.instance(listener.context).students.getAll() as ArrayList
            params.size == 1 -> MainDatabase.instance(listener.context).students.getAllByBrigade(params[0]!!) as ArrayList
            else -> ArrayList(0)
        }
    }

    override fun onPostExecute(result: ArrayList<Student>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}