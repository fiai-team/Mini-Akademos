package uci.fiai.miniakd.tasks

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

abstract class AndroidViewModelListener(application: Application) : AndroidViewModel(application) {

    abstract fun context(): Context
    abstract fun onTaskFinished(result: List<Any>, taskName: String)
}