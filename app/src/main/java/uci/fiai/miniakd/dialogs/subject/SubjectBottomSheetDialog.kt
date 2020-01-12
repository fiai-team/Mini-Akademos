package uci.fiai.miniakd.dialogs.subject

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

class SubjectBottomSheetDialog(private var listener: AddSubjectListener, private val subject: Subject?) : BottomSheetDialogFragment() {

    interface AddSubjectListener {
        /**
         * @param subject Student objective of the interaction
         * @param isEditionOperation A [Boolean] value showing if the [subject] is new or are in edition.
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

        if (subject != null) {
            nameEditText.setText(subject.name)
            countHoursClassEditText.setText(subject.hoursCount.toString())
            spinner.setSelection(subject.period)
            hasFinalExamCheckBox.isChecked = subject.hasFinalExam
            hasPartialsEvaluationsCheckBox.isChecked = subject.hasPartialsExams

            addSubjectButton.setText(R.string.saveAction)
        } else {
            addSubjectButton.setText(R.string.addAction)
        }

        addSubjectButton.setOnClickListener {
            if (subject == null) {
                listener.onAddSubjectInteraction(getSubject(), isEditionOperation = false)
            } else {
                listener.onAddSubjectInteraction(getSubject(), isEditionOperation = true)
            }
            this@SubjectBottomSheetDialog.dismiss()
        }
    }

    private fun getSubject(): Subject {
        val subject = this.subject ?: Subject()
        subject.name = nameEditText.text.toString()
        subject.hoursCount = countHoursClassEditText.text.toString().toInt()
        subject.period = spinner.selectedItemPosition
        subject.hasFinalExam = hasFinalExamCheckBox.isChecked
        subject.hasPartialsExams = hasPartialsEvaluationsCheckBox.isChecked
        return subject
    }

    companion object {
        const val TAG = "ADD_SUBJECT_BOTTOM_SHEET_DIALOG"

        fun newInstance(listener: AddSubjectListener): SubjectBottomSheetDialog {
            return SubjectBottomSheetDialog(listener, null)
        }

        fun newInstance(listener: AddSubjectListener, subject: Subject): SubjectBottomSheetDialog {
            return SubjectBottomSheetDialog(listener, subject)
        }
    }
}