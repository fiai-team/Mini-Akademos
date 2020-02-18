package uci.fiai.miniakd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerviewitem_brigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Brigade

class BrigadesRecyclerViewAdapter (private val context: Context, private val listener: OnBrigadesRecyclerViewAdapterListener, private val brigades: ArrayList<Brigade>) : RecyclerView.Adapter<BrigadesRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerviewitem_brigade, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {
            listener.onBrigadeItemLongClick(brigades[position], position)
            true
        }
        holder.itemView.setOnClickListener {
            listener.onBrigadeItemClick(brigades[position], position)
        }
        holder.nameTextView.text = brigades[position].name
        with(context) {
            when (val studentsCount = brigades[position].studentsCount) {
                0 -> holder.countTextView.text = getString(R.string.students_count_empty)
                1 -> holder.countTextView.text = getString(R.string.student_count_singular, studentsCount)
                else -> holder.countTextView.text = getString(R.string.student_count_plural, studentsCount)
            }
        }
    }

    override fun getItemCount(): Int {
        return brigades.count()
    }

    fun removeItem(position: Int) {
        brigades.remove(brigades[position])
        notifyItemRemoved(position)
    }

    fun restoreItem(brigade: Brigade, position: Int) {
        brigades.add(position, brigade)
        notifyItemInserted(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.nameTextView
        val countTextView: TextView = itemView.countTextView
    }

    interface OnBrigadesRecyclerViewAdapterListener {
        fun onBrigadeItemClick(brigade: Brigade, position: Int)
        fun onBrigadeItemLongClick(brigade: Brigade, position: Int)
    }

}
