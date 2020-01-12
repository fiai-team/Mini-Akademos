package uci.fiai.miniakd.fragments.subjects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.bottomsheetdialog_addsubject.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.dialogs.addsubject.AddSubjectBottomSheetDialog

class SubjectsFragment : Fragment(), SpeedDialView.OnActionSelectedListener,
    AddSubjectBottomSheetDialog.AddSubjectListener {


    private lateinit var viewModel: SubjectsFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(SubjectsFragmentViewModel::class.java)
        return inflater.inflate(R.layout.fragment_subjects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val speedDialView = view.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(R.menu.subjects_speeddial)
        speedDialView.setOnActionSelectedListener(this)


    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        when (actionItem?.id) {
            R.id.addSubjectItem -> {
                fragmentManager?.let {
                    AddSubjectBottomSheetDialog.newInstance(this@SubjectsFragment).show(it, AddSubjectBottomSheetDialog.TAG)
                }
            }
        }
        return false
    }

    override fun onAddSubjectInteraction(subject: Subject, isEditionOperation: Boolean) {
        context?.let {
            if (isEditionOperation)
                viewModel.updateSubject(subject)
            else
                viewModel.insertSubject(subject)
        }
    }

    companion object {
        fun newInstance() = SubjectsFragment()
    }

}
