package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Student

class LoadStudentsByBrigadeAsyncTask(private val listener: AndroidViewModelListener) : AsyncTask<Int, Void, List<Student>>() {

    override fun doInBackground(vararg params: Int?): List<Student>? {
        return when {
            params.isEmpty() -> MainDatabase.instance(listener.context()).students().getAll()
            params.size == 1 -> MainDatabase.instance(listener.context()).students().getAllByBrigade(params[0]!!)
            else -> ArrayList(0)
        }
    }

    override fun onPostExecute(result: List<Student>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}