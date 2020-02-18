package uci.fiai.miniakd.tasks

import android.os.AsyncTask
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student

class LoadBrigadesAsyncTask(private val listener: ViewModelListener) : AsyncTask<Boolean, Void, ArrayList<Brigade>>() {

    override fun doInBackground(vararg params: Boolean?): ArrayList<Brigade> {
        when (params.size) {
            0 -> {
                return MainDatabase.instance(listener.context).brigades.getAll() as ArrayList<Brigade>
            }
            1 -> {
                val brigades =  MainDatabase.instance(listener.context).brigades.getAll()
                for (brigade in brigades) {
                    brigade.studentsCount = MainDatabase.instance(listener.context).brigades.getStudentsByBrigadeId(brigade.id).count()
                }
                return brigades as ArrayList<Brigade>
            }
        }
        return MainDatabase.instance(listener.context).brigades.getAll() as ArrayList<Brigade>
    }

    override fun onPostExecute(result: ArrayList<Brigade>?) {
        super.onPostExecute(result)

        listener.onTaskFinished(result!!, this.javaClass.name)
    }
}