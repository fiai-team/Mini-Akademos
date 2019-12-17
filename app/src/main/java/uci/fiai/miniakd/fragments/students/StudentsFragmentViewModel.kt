package uci.fiai.miniakd.fragments.students

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask
import uci.fiai.miniakd.tasks.LoadStudentsByBrigadeAsyncTask

class StudentsFragmentViewModel(application: Application) : AndroidViewModel(application), AndroidViewModelListener {

    //region Mutable Fields
    private var _studentsList = MutableLiveData<List<Student>>().apply {
        this.value = ArrayList(0)
    }
    private var _brigadesList = MutableLiveData<List<Brigade>>().apply {
        this.value = ArrayList(0)
    }
    //endregion

    //region LiveData Properties
    val studentsList: LiveData<List<Student>> = _studentsList
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
            LoadStudentsByBrigadeAsyncTask::class.qualifiedName -> {
                _studentsList.apply {
                    this.value = result as List<Student>
                }
            }
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
            MainDatabase.instance(context()).brigades().insertAll(Brigade(name))
            update()
        }
        thread.start()
    }

    private fun update() {
        val asyncTack1 = LoadStudentsByBrigadeAsyncTask(this)
        asyncTack1.execute()
        val asyncTack2 = LoadBrigadesAsyncTask(this)
        asyncTack2.execute()
    }

    fun addStudent(student: Student) {
        val thread = Thread {
            MainDatabase.instance(context()).students().insertAll(student)
            update()
        }
        thread.start()
    }

}