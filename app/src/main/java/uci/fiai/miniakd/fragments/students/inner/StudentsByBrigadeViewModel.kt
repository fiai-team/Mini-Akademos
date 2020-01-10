package uci.fiai.miniakd.fragments.students.inner

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask
import uci.fiai.miniakd.tasks.LoadStudentsByBrigadeAsyncTask

class StudentsByBrigadeViewModel (application: Application) : AndroidViewModel(application), AndroidViewModelListener {

    private var brigadeArg: Int = 0
    //region Mutable Fields
    private var _studentsList = MutableLiveData<ArrayList<Student>>().apply {
        this.value = ArrayList(0)
    }
    //endregion

    //region LiveData Properties
    val studentsList: LiveData<ArrayList<Student>> = _studentsList
    //endregion

    override fun context(): Context {
        return getApplication<Application>().applicationContext
    }

    override fun onTaskFinished(result: List<Any>, taskName: String) {
        when(taskName) {
            LoadStudentsByBrigadeAsyncTask::class.qualifiedName -> {
                _studentsList.apply {
                    this.value = result as ArrayList<Student>
                }
            }
        }
    }

    fun init(brigadeArg: Int) {
        this.brigadeArg = brigadeArg
        update()
    }

    private fun update() {
        val asyncTack1 = LoadStudentsByBrigadeAsyncTask(this)
        asyncTack1.execute(brigadeArg)
    }

    fun removeStudent(student: Student) {

    }

    fun markToRemove(student: Student, position: Int) {
        studentsList.value?.get(position)?.isRemoving = true
    }
}