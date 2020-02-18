package uci.fiai.miniakd.fragments.students.inner

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.LoadStudentsByBrigadeAsyncTask
import uci.fiai.miniakd.tasks.ViewModelListener

class StudentsByBrigadeViewModel (context: Context, private val brigadeArg: Int) : ViewModelListener(context) {

    //region Mutable Fields
    private var _studentsList = MutableLiveData<ArrayList<Student>>()
    //endregion

    //region LiveData Properties
    val studentsList: LiveData<ArrayList<Student>> = _studentsList
    //endregion

    init {
        update()
    }

    override fun onTaskFinished(result: ArrayList<*>, taskName: String) {
        when(taskName) {
            LoadStudentsByBrigadeAsyncTask::class.qualifiedName -> {
                _studentsList.apply {
                    this.value =  result.filterIsInstance<Student>() as ArrayList<Student>
                }
            }
        }
    }
    private fun update() {
        val asyncTack1 = LoadStudentsByBrigadeAsyncTask(this)
        asyncTack1.execute(brigadeArg)
    }

    fun removeStudent(student: Student) {
        Thread {
            MainDatabase.instance(context).students.delete(student)
            update()
        }.start()
    }

    fun saveEditStudent(student: Student) {
        Thread {
            MainDatabase.instance(context).students.update(student)
            update()
        }.start()
    }
}