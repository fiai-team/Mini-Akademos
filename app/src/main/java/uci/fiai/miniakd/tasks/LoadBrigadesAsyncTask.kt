package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student

class LoadBrigadesAsyncTask(private val listener: AndroidViewModelListener) : AsyncTask<Boolean, Void, List<Brigade>>() {

    override fun doInBackground(vararg params: Boolean?): List<Brigade> {
        when (params.size) {
            0 -> {
                return MainDatabase.instance(listener.context()).brigades.getAll()
            }
            1 -> {
                val brigades =  MainDatabase.instance(listener.context()).brigades.getAll()
                for (brigade in brigades) {
                    brigade.studentsCount = MainDatabase.instance(listener.context()).brigades.getStudentsByBrigadeId(brigade.id).count()
                }
                return brigades
            }
        }
        return MainDatabase.instance(listener.context()).brigades.getAll()
    }

    override fun onPostExecute(result: List<Brigade>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}