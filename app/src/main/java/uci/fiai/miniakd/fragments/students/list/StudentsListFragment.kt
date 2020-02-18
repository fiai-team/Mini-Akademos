package uci.fiai.miniakd.fragments.students.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uci.fiai.miniakd.R
import uci.fiai.miniakd.adapters.StudentsListRecyclerViewAdapter
import uci.fiai.miniakd.database.entities.Student


class StudentsListFragment : Fragment(), StudentsListRecyclerViewAdapter.OnListFragmentInteractionListener {
    private var brigadeId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            brigadeId = it.getInt(BRIGADE_ID_ARG)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

        if (view is RecyclerView) {
            with(view) {                
                adapter = StudentsListRecyclerViewAdapter(arrayListOf(), this@StudentsListFragment)
            }
        }
        return view
    }

    override fun onListFragmentInteraction(item: Student) {

    }

    companion object {
        const val BRIGADE_ID_ARG = "BRIGADE_ID_ARG"

        @JvmStatic
        fun newInstance(brigadeId: Int) =
            StudentsListFragment().apply {
                arguments = Bundle().apply {
                    putInt(BRIGADE_ID_ARG, brigadeId)
                }
            }
    }
}
