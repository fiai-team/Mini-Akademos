package uci.fiai.miniakd.fragments.subjects

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.tasks.AndroidViewModelListener
import uci.fiai.miniakd.tasks.LoadSubjectsAsyncTask

class SubjectsFragmentViewModel(application: Application) : AndroidViewModelListener(application) {

    val subjectsList = MutableLiveData<ArrayList<Subject>>()

    init {
        update()
    }

    override fun context(): Context {
        return getApplication()
    }

    override fun onTaskFinished(result: List<Any>, taskName: String) {
        when (taskName) {
            LoadSubjectsAsyncTask::class.qualifiedName -> {
                subjectsList.apply {
                    this.value = result as ArrayList<Subject>
                }
            }
        }
    }

    fun update() {
        LoadSubjectsAsyncTask(this).execute()
    }


    fun insertSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context()).subjects.insert(subject)
        }.start()
    }

    fun updateSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context()).subjects.update(subject)
            update()
        }.start()
    }

    fun removeSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context()).subjects.delete(subject)
            update()
        }.start()
    }

}
