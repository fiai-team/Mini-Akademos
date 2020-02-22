package uci.fiai.miniakd.fragments.turns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.badoualy.datepicker.DatePickerTimeline
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragment_turns.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.adapters.TurnsRecyclerViewAdapter
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.dialogs.turns.TurnsBottomSheetDialog
import uci.fiai.miniakd.extensions.getDay
import uci.fiai.miniakd.extensions.getMonth
import uci.fiai.miniakd.extensions.getYear
import uci.fiai.miniakd.extensions.selectedDateFormatted
import uci.fiai.miniakd.fragments.turns.details.TurnDetailsFragment
import java.util.*

class TurnsFragment : Fragment(), SpeedDialView.OnActionSelectedListener, TurnsBottomSheetDialog.AddTurnListener,
    TurnsRecyclerViewAdapter.TurnsRecyclerViewAdapterListener {


    private lateinit var viewModel: TurnsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.apply {
            viewModel = TurnsFragmentViewModel(this)
        }
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_turns, container, false)

        val speedDialView = root.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(R.menu.turns_speeddial)
        speedDialView.setOnActionSelectedListener(this)

        viewModel.turnForDateList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                emptyTurnsTextView.visibility = View.VISIBLE
                emptyTurnsTextView.text = getString(R.string.emptyTurns, turnsDatePickerTimeline.selectedDateFormatted())
            } else {
                emptyTurnsTextView.visibility = View.GONE
            }
            context?.apply {
                turnsRecyclerView.adapter = TurnsRecyclerViewAdapter(this, this@TurnsFragment, it)
            }
        })

        val turnsDatePickerTimeline = root.findViewById<DatePickerTimeline>(R.id.turnsDatePickerTimeline)
        turnsDatePickerTimeline.setOnDateSelectedListener { year, month, day, _ ->
            viewModel.selectedDate.apply {
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.set(year, month, day)
                this.value = calendar

                viewModel.updateTurns()
            }
        }
        return root
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        when (actionItem?.id) {
            R.id.todayItem -> {
                showToday()
            }
            R.id.addTurnItem -> {
                parentFragmentManager.let {
                    TurnsBottomSheetDialog.newInstance(this@TurnsFragment, viewModel.brigadesList.value!!, viewModel.subjectsList.value!!, getDate()
                    ).show(it, TurnsBottomSheetDialog.TAG)
                }
            }
        }
        return false
    }

    override fun onAddTurnInteraction(turn: Turn?, isEditionOperation: Boolean) {
        if (isEditionOperation){

        } else {
            viewModel.insertTurn(turn!!)
        }
    }

    override fun onTurnItemClick(turn: Turn, position: Int) {
        val args = Bundle()
        args.putInt(TurnDetailsFragment.TURN_ID_ARG, turn.id)
        findNavController().navigate(R.id.action_nav_turns_to_nav_turns_details, args)
    }

    private fun showToday() {
        val calendar = Calendar.getInstance(Locale.getDefault())
        turnsDatePickerTimeline.setSelectedDate(calendar.getYear(), calendar.getMonth(), calendar.getDay())
    }

    private fun showDialogEditSubject(turn: Turn) {
        parentFragmentManager.let {
            TurnsBottomSheetDialog.newInstance(this@TurnsFragment, turn, viewModel.brigadesList.value!!, viewModel.subjectsList.value!!, getDate()
            ).show(it, TurnsBottomSheetDialog.TAG)
        }
    }

    private fun getDate(datePickerTimeline: DatePickerTimeline? = null): Calendar {
        if (datePickerTimeline == null)
            with(turnsDatePickerTimeline) {
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.set(selectedYear, selectedMonth, selectedDay)
                return calendar
            }
        else {
            with(datePickerTimeline) {
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.set(selectedYear, selectedMonth, selectedDay)
                return calendar
            }
        }
    }

}
