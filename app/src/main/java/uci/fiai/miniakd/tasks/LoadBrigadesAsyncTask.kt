package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student

class LoadBrigadesAsyncTask(private val listener: AndroidViewModelListener) :
    AsyncTask<Void, Void, List<Brigade>>() {

    override fun doInBackground(vararg params: Void?): List<Brigade> {
        return MainDatabase.instance(listener.context()).brigades().getAll()
    }

    override fun onPostExecute(result: List<Brigade>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}