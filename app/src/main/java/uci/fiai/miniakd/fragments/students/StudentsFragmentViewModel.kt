package uci.fiai.miniakd.fragments.students

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadStudentsByBrigadeAsyncTask

@Suppress("UNCHECKED_CAST")
class StudentsFragmentViewModel(application: Application) : AndroidViewModel(application),
    AndroidViewModelListener {
    override fun context(): Context {
        return getApplication<Application>().applicationContext
    }

    //region Mutable Fields
    private val _studentsList = MutableLiveData<List<Student>>().apply {
        LoadStudentsByBrigadeAsyncTask(this@StudentsFragmentViewModel).execute()

    }
    //endregion

    //region LiveData Properties
    val studentsList: LiveData<List<Student>> = _studentsList
    //endregion

    override fun onTaskFinished(result: List<Any>, taskName: String) {
        if (taskName == LoadStudentsByBrigadeAsyncTask::class.simpleName) {
            _studentsList.apply {
                this.value = result as List<Student>
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text
}