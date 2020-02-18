package uci.fiai.miniakd.tasks

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

abstract class ViewModelListener(val context: Context) : ViewModel() {

    abstract fun onTaskFinished(result: ArrayList<*>, taskName: String)
}