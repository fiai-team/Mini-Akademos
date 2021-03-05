package uci.fiai.miniakd.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerviewitem_studentsbybrigade.view.*

import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Student

class StudentsListRecyclerViewAdapter(private val students: ArrayList<Student>, private val listener: OnListFragmentInteractionListener) : RecyclerView.Adapter<StudentsListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerviewitem_studentsbybrigade, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.nameTextView.text = student.name

        with(holder.view) {
            setOnClickListener {
                listener.onListFragmentInteraction(student)
            }
        }
    }

    override fun getItemCount(): Int = students.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Student)
    }
}
