package uci.fiai.miniakd.fragments.brigades

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask
import uci.fiai.miniakd.tasks.ViewModelListener

class BrigadesFragmentViewModel(context: Context) : ViewModelListener(context) {

    //region Mutable fields
    private val _brigadesList = MutableLiveData<ArrayList<Brigade>>()
    //endregion

    //region LiveData
    val brigadesList: LiveData<ArrayList<Brigade>> = _brigadesList
    //endregion

    init {
        update()
    }


    override fun onTaskFinished(result: ArrayList<*>, taskName: String) {
        when(taskName) {
            LoadBrigadesAsyncTask::class.qualifiedName -> {
                _brigadesList.apply {
                    this.value = result.filterIsInstance<Brigade>() as ArrayList<Brigade>
                }
            }
        }
    }

    fun update() {
        val task1 = LoadBrigadesAsyncTask(this)
        task1.execute(true)
    }

    fun removeBrigade(brigade: Brigade) {
        Thread {
            MainDatabase.instance(context).brigades.delete(brigade)
            update()
        }.start()
    }

    fun addBrigade(name: String) {
        Thread {
            MainDatabase.instance(context).brigades.insertAll(Brigade(name))
            update()
        }.start()
    }
}