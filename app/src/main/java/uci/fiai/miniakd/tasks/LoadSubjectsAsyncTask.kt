package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Subject

class LoadSubjectsAsyncTask (private val listener: AndroidViewModelListener) : AsyncTask<Boolean, Void, List<Subject>>() {

    override fun doInBackground(vararg params: Boolean?): List<Subject> {
        return MainDatabase.instance(listener.context()).subjects.getAll()
    }

    override fun onPostExecute(result: List<Subject>) {
        super.onPostExecute(result)

        listener.onTaskFinished(result, this.javaClass.name)
    }
}