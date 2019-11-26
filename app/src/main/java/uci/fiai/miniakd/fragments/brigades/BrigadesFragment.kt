package uci.fiai.miniakd.fragments.brigades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uci.fiai.miniakd.R

class BrigadesFragment : Fragment() {

    private lateinit var brigadesFragmentViewModel: BrigadesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        brigadesFragmentViewModel = ViewModelProviders.of(this).get(BrigadesFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragments_brigades, container, false)

        return root
    }
}