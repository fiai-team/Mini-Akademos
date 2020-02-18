package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Subject

class LoadSubjectsAsyncTask (private val listener: ViewModelListener) : AsyncTask<Boolean, Void, ArrayList<Subject>>() {

    override fun doInBackground(vararg params: Boolean?): ArrayList<Subject> {
        return MainDatabase.instance(listener.context).subjects.getAll() as ArrayList
    }

    override fun onPostExecute(result: ArrayList<Subject>) {
        super.onPostExecute(result)

        listener.onTaskFinished(result, this.javaClass.name)
    }
}