package uci.fiai.miniakd.fragments.subjects

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uci.fiai.miniakd.R


class SubjectsFragment : Fragment() {

    private lateinit var viewModel: SubjectsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subjects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SubjectsFragmentViewModel::class.java)
    }

    companion object {
        fun newInstance() = SubjectsFragment()
    }

}
