package uci.fiai.miniakd.fragments.students.inner

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadStudentsByBrigadeAsyncTask

class StudentsByBrigadeViewModel (application: Application) : AndroidViewModel(application), AndroidViewModelListener {

    //region Mutable Fields
    private var _studentsList = MutableLiveData<List<Student>>().apply {
        this.value = ArrayList(0)
    }
    //endregion

    //region LiveData Properties
    val studentsList: LiveData<List<Student>> = _studentsList
    //endregion

    init {
        update()
    }


    override fun context(): Context {
        return getApplication<Application>().applicationContext
    }

    override fun onTaskFinished(result: List<Any>, taskName: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun update() {
        val asyncTack1 = LoadStudentsByBrigadeAsyncTask(this)
        asyncTack1.execute()
    }
}