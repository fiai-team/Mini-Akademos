package uci.fiai.miniakd.fragments.subjects

import android.app.Application
import android.content.Context
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.tasks.AndroidViewModelListener

class SubjectsFragmentViewModel(application: Application) : AndroidViewModelListener(application) {

    override fun context(): Context {
        return getApplication()
    }

    override fun onTaskFinished(result: List<Any>, taskName: String) {

    }

    fun insertSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context()).subjects.insert(subject)
        }.start()
    }

    fun updateSubject(subject: Subject) {
        Thread {
            MainDatabase.instance(context()).subjects.update(subject)
        }.start()
    }

}
