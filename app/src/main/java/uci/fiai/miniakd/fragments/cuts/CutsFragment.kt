package uci.fiai.miniakd.fragments.cuts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uci.fiai.miniakd.R

class CutsFragment : Fragment() {

    private lateinit var cutsFragmentViewModel: CutsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        cutsFragmentViewModel =
            ViewModelProviders.of(this).get(CutsFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_cuts, container, false)
        return root
    }
}