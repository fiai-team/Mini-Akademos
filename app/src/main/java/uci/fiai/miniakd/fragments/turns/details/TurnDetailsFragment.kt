package uci.fiai.miniakd.fragments.turns.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_turn_details.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.adapters.StudentAssistanceRecyclerViewAdapter

class TurnDetailsFragment : Fragment() {

    private lateinit var viewModel: TurnDetailsViewModel
    private var showAssistance = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*arguments?.apply {
            val turnId = getInt(TURN_ID_ARG, 1)
            context?.apply {
                viewModel = TurnDetailsViewModel(this, turnId)
            }
        }*/
        context?.apply {
            viewModel = TurnDetailsViewModel(this, 1)
        }
        return inflater.inflate(R.layout.fragment_turn_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.studentsList.observe(viewLifecycleOwner, Observer {
            context?.apply {
                recyclerView.adapter = StudentAssistanceRecyclerViewAdapter(this, it)
            }
        })
    }

    companion object {
        const val TURN_ID_ARG = "TURN_ID_ARG"
    }
}
