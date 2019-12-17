package uci.fiai.miniakd.fragments.students.inner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_studentsbybrigade.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Student

class StudentsByBrigadeListFragment : Fragment() {

    private lateinit var viewModel: StudentsByBrigadeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(StudentsByBrigadeViewModel::class.java)
        val root =  inflater.inflate(R.layout.fragment_studentsbybrigade, container, false)

        viewModel.studentsList.observe(this, Observer {
            if (it.isEmpty()) {
                recyclerView.visibility = View.GONE
            } else {
                recyclerView.visibility = View.VISIBLE
                recyclerView.adapter = StudentsByBrigadeAdapter(context!!, it)
            }
        })

        return root
    }

    inner class StudentsByBrigadeAdapter(private val context: Context, private val students: List<Student>) : RecyclerView.Adapter<StudentsByBrigadeAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.recyclerviewitem_studentsbybrigade, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return students.count()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }
}