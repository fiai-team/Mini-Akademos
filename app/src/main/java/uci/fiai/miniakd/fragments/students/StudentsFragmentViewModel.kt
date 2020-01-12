package uci.fiai.miniakd.fragments.students

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.addStudent
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask

class StudentsFragmentViewModel(application: Application) : AndroidViewModelListener(application) {

    //region Mutable Fields
    private var _brigadesList = MutableLiveData<List<Brigade>>().apply {
        this.value = ArrayList(0)
    }
    //endregion

    val brigadesList: LiveData<List<Brigade>> = _brigadesList
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
                    this.value = result as List<Brigade>
                }
            }
        }
    }

    fun canAddStudent(): Boolean {
        return _brigadesList.value!!.isNotEmpty()
    }

    fun addBrigade(name: String) {
        val thread = Thread {
            MainDatabase.instance(context()).brigades.insertAll(Brigade(name))
            update()
        }
        thread.start()
    }

    private fun update() {
        val asyncTack2 = LoadBrigadesAsyncTask(this)
        asyncTack2.execute()
    }

    fun addStudent(student: Student) {
        Thread {
            addStudent(context(), student)
            update()
        }.start()
    }

}


