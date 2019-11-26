package uci.fiai.miniakd.fragments.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uci.fiai.miniakd.R

class StudentsFragment : Fragment() {

    private lateinit var studentsFragmentViewModel: StudentsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        studentsFragmentViewModel =
            ViewModelProviders.of(this).get(StudentsFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_students, container, false)

        return root
    }
}