package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Student

class LoadStudentsByBrigadeAsyncTask(private val listener: AndroidViewModelListener) :
    AsyncTask<Void, Void, List<Student>>() {

    override fun doInBackground(vararg params: Void?): List<Student> {
        return MainDatabase.instance(listener.context()).students().getAll()
    }

    override fun onPostExecute(result: List<Student>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}