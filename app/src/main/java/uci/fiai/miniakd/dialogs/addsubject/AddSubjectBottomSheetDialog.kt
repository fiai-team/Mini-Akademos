package uci.fiai.miniakd.dialogs.addsubject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheetdialog_addsubject.*
import kotlinx.android.synthetic.main.bottomsheetdialog_addsubject.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Subject

class AddSubjectBottomSheetDialog(private var listener: AddSubjectListener, private var subject: Subject?) : BottomSheetDialogFragment() {

    interface AddSubjectListener {
        /**
         * @param student Student objective of the interaction
         * @param isEditionOperation A [Boolean] value showing if the [student] is new or are in edition.
         */
        fun onAddSubjectInteraction(subject: Subject, isEditionOperation: Boolean)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheetdialog_addsubject, container, false)
        context?.let {
            view.spinner.adapter = ArrayAdapter<String>(it, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.periodsNamesArray))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addSubjectButton.setOnClickListener {
            if (subject == null) {
                val subject = Subject()
                subject.name = nameEditText.text.toString()
                subject.hoursCount = countHoursClassEditText.text.toString().toInt()
                subject.period = spinner.selectedItemPosition
                subject.hasFinalExam = hasFinalExamCheckBox.isChecked
                subject.hasPartialsExams = hasPartialsEvaluationsCheckBox.isChecked
                listener.onAddSubjectInteraction(subject, isEditionOperation = false)
            } else {
                subject?.let {
                    listener.onAddSubjectInteraction(it, isEditionOperation = false)
                }
            }


            this@AddSubjectBottomSheetDialog.dismiss()
        }
    }

    companion object {
        const val TAG = "ADD_SUBJECT_BOTTOM_SHEET_DIALOG"

        fun newInstance(listener: AddSubjectListener): AddSubjectBottomSheetDialog {
            return AddSubjectBottomSheetDialog(listener, null)
        }

        fun newInstance(listener: AddSubjectListener, subject: Subject): AddSubjectBottomSheetDialog {
            return AddSubjectBottomSheetDialog(listener, subject)
        }
    }
}