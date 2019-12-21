package uci.fiai.miniakd.fragments.brigades

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragments_brigades.*
import kotlinx.android.synthetic.main.recyclerviewitem_brigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Brigade

class BrigadesFragment : Fragment() {

    var callback: ItemTouchHelper.Callback? = null

    private lateinit var viewModel: BrigadesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(BrigadesFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragments_brigades, container, false)

        viewModel.brigadesList.observe(this, Observer {
            if (context != null)
                recyclerView.adapter = BrigadesRecyclerViewAdapter(context!!, it)
        })

        return root
    }

    inner class BrigadesRecyclerViewAdapter(private val context: Context, private val brigades: List<Brigade>) : RecyclerView.Adapter<BrigadesRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.recyclerviewitem_brigade, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.nameTextView.text = brigades[position].name
            when (val studentsCount = brigades[position].studentsCount) {
                0 -> holder.countTextView.visibility = View.GONE
                1 -> holder.countTextView.text = getString(R.string.student_count_singular, studentsCount)
                else -> holder.countTextView.text = getString(R.string.student_count_plural, studentsCount)
            }
        }

        override fun getItemCount(): Int {
            return brigades.count()
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.nameTextView
            val countTextView: TextView = itemView.countTextView
        }
    }
}