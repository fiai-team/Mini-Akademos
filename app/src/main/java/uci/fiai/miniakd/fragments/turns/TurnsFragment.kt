package uci.fiai.miniakd.fragments.turns

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.leinardi.android.speeddial.*
import kotlinx.android.synthetic.main.fragment_turns.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.extensions.getDay
import uci.fiai.miniakd.extensions.getMonth
import uci.fiai.miniakd.extensions.getYear
import uci.fiai.miniakd.extensions.selectedDateFormatted
import java.util.*

class TurnsFragment : Fragment(), SpeedDialView.OnActionSelectedListener {

    private lateinit var viewModel: TurnsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(TurnsFragmentViewModel::class.java)
        return inflater.inflate(R.layout.fragment_turns, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val speedDialView = view.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(R.menu.turns_speeddial)
        speedDialView.setOnActionSelectedListener(this)

        viewModel.turnForDate.observe(this, Observer {
            if (it.isEmpty()) {
                emptyTurnsTextView.visibility = View.VISIBLE
                emptyTurnsTextView.text = getString(R.string.emptyTurns, turnsDatePickerTimeline.selectedDateFormatted())
            } else {
                emptyTurnsTextView.visibility = View.GONE
            }
        })

        turnsDatePickerTimeline.setOnDateSelectedListener { year, month, day, index ->
            viewModel.selectedDate.apply {
                val calendar = Calendar.getInstance(Locale.getDefault())
                calendar.set(year, month, day)
                this.value = calendar
            }
        }
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        when (actionItem?.id) {
            R.id.todayItem -> {
                showToday()
            }
        }
        return false
    }

    private fun showToday() {
        val calendar = Calendar.getInstance(Locale.getDefault())
        turnsDatePickerTimeline.setSelectedDate(calendar.getYear(), calendar.getMonth(), calendar.getDay())
    }

}
