package uci.fiai.miniakd.fragments.subjects

import android.content.Context
import androidx.lifecycle.MutableLiveData
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.tasks.LoadSubjectsAsyncTask
import uci.fiai.miniakd.tasks.ViewModelListener

class SubjectsFragmentViewModel(context: Context) : ViewModelListener(context) {

    val subjectsList = MutableLiveData<ArrayList<Subject>>()

    init {
        update()
    }

    override fun onTaskFinished(result: ArrayList<*>, taskName: String) {
        when (taskName) {
            LoadSubjectsAsyncTask::class.qualifiedName -> {
                subjectsList.apply {
                    this.value = result.filterIsInstance<Subject>() as ArrayList<Subject>
                }
            }
        }
    }

    fun update() {
        LoadSubjectsAsyncTask(this).execute()
    }


    fun insertSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context).subjects.insert(subject)
            update()
        }.start()
    }

    fun updateSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context).subjects.update(subject)
            update()
        }.start()
    }

    fun removeSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context).subjects.delete(subject)
            update()
        }.start()
    }

}
