package uci.fiai.miniakd.fragments.turns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.turns_fragment.*
import kotlinx.android.synthetic.main.turns_fragment.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.extensions.selectedDateFormatted

class TurnsFragment : Fragment() {

    private lateinit var viewModel: TurnsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(TurnsFragmentViewModel::class.java)
        return inflater.inflate(R.layout.turns_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.turnForDate.observe(this, Observer {
            if (it.isEmpty()) {
                emptyTurnsTextView.visibility = View.VISIBLE
                emptyTurnsTextView.text = getString(R.string.emptyTurns, turnsDatePickerTimeline.selectedDateFormatted())
            } else {
                emptyTurnsTextView.visibility = View.GONE
            }
        })
    }
}
