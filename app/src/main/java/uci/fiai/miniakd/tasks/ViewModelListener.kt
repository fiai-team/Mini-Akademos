package uci.fiai.miniakd.tasks

import android.content.Context
import androidx.lifecycle.ViewModel

abstract class ViewModelListener(val context: Context) : ViewModel() {

    abstract fun onTaskFinished(result: ArrayList<*>, taskName: String)
}