package uci.fiai.miniakd.fragments.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uci.fiai.miniakd.R

class AboutFragment : Fragment() {

    private lateinit var aboutFragmentViewModel: AboutFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutFragmentViewModel =
            ViewModelProviders.of(this).get(AboutFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about, container, false)
        val textView: TextView = root.findViewById(R.id.text_tools)
        aboutFragmentViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}