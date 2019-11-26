package uci.fiai.miniakd.fragments.evaluations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uci.fiai.miniakd.R

class EvaluationsFragment : Fragment() {

    private lateinit var evaluationsFragmentViewModel: EvaluationsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        evaluationsFragmentViewModel =
            ViewModelProviders.of(this).get(EvaluationsFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_evaluations, container, false)

        return root
    }
}