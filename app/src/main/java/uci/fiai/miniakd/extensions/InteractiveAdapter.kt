package uci.fiai.miniakd.extensions

import androidx.recyclerview.widget.RecyclerView

abstract class InteractiveAdapter<T, R: RecyclerView.ViewHolder> : RecyclerView.Adapter<R>() {

    abstract fun removeItem(position: Int)

    abstract fun restoreItem(obj: T, position: Int)

}