package uci.fiai.miniakd.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerviewitem_studentassistance.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Student

class StudentAssistanceRecyclerViewAdapter(val context: Context, private val students: ArrayList<Student>) : RecyclerView.Adapter<StudentAssistanceRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerviewitem_studentassistance, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {

        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val assistanceTextView = view.assistanceTextView
    }
}
