package uci.fiai.miniakd.fragments.students

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.addStudent
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.LoadBrigadesAsyncTask
import uci.fiai.miniakd.tasks.ViewModelListener

class StudentsFragmentViewModel(context: Context) : ViewModelListener(context) {

    //region Mutable Fields
    private var _brigadesList = MutableLiveData<ArrayList<Brigade>>()
    //endregion

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

    fun canAddStudent(): Boolean {
        return _brigadesList.value!!.isNotEmpty()
    }

    fun addBrigade(name: String) {
        Thread {
            MainDatabase.instance(context).brigades.insertAll(Brigade(name))
            update()
        }.start()
    }

    private fun update() {
        val asyncTack2 = LoadBrigadesAsyncTask(this)
        asyncTack2.execute()
    }

    fun addStudent(student: Student) {
        Thread {
            addStudent(context, student)
            update()
        }.start()
    }

}


