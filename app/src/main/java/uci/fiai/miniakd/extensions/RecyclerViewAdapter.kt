package uci.fiai.miniakd.extensions

import androidx.recyclerview.widget.RecyclerView
import uci.fiai.miniakd.database.entities.Student

fun  RecyclerView.Adapter<RecyclerView.ViewHolder>?.restoreItem(student: Student, position: Int) {

}

inline fun <reified T> RecyclerView.Adapter<RecyclerView.ViewHolder>?.removeItem(position: Int) {
    if (this is T) {

    }
}
