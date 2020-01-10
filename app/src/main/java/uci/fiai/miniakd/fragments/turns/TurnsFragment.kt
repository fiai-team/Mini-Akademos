package uci.fiai.miniakd.fragments.turns

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

import uci.fiai.miniakd.R

class TurnsFragment : Fragment() {

    companion object {
        fun newInstance() = TurnsFragment()
    }

    private lateinit var viewModel: TurnsFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.turns_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TurnsFragmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
