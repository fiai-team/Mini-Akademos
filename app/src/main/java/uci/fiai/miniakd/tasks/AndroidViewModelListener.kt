package uci.fiai.miniakd.tasks

import android.content.Context

interface AndroidViewModelListener {

    fun context(): Context


    fun onTaskFinished(result: List<Any>, taskName: String)
}