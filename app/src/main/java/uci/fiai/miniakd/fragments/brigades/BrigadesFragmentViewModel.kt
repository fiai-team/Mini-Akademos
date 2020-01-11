package uci.fiai.miniakd.fragments.brigades

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask

class BrigadesFragmentViewModel(application: Application) : AndroidViewModel(application), AndroidViewModelListener {

    //region Mutable fields
    private val _brigadesList = MutableLiveData<ArrayList<Brigade>>()
    //endregion

    //region LiveData
    val brigadesList: LiveData<ArrayList<Brigade>> = _brigadesList
    //endregion

    init {
        update()
    }

    override fun context(): Context {
        return getApplication<Application>().applicationContext
    }

    override fun onTaskFinished(result: List<Any>, taskName: String) {
        when(taskName) {
            LoadBrigadesAsyncTask::class.qualifiedName -> {
                _brigadesList.apply {
                    this.value = result as ArrayList<Brigade>
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
            MainDatabase.instance(context()).brigades().delete(brigade)
            update()
        }.start()
    }
}