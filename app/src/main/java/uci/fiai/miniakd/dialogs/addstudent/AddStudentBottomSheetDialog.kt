package uci.fiai.miniakd.dialogs.addstudent

import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheetdialog_addstudent.*
import kotlinx.android.synthetic.main.bottomsheetdialog_addstudent.spinner
import kotlinx.android.synthetic.main.bottomsheetdialog_addstudent.view.*
import kotlinx.android.synthetic.main.spinneritem_brigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.*

class AddStudentBottomSheetDialog(private var listener: AddStudentListener, private val brigades: List<Brigade>? = null, private val student: Student? = null) : BottomSheetDialogFragment() {

    interface AddStudentListener {
        /**
         * @param student Student objetive of the interaction
         * @param isEditionOperation A [Boolean] value showing if the [student] is new or are in edition.
         */
        fun onAddStudentInteraction(student: Student, isEditionOperation: Boolean)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheetdialog_addstudent, container, false)
        if (brigades == null) {
            view.spinner.visibility = View.GONE
        } else {
            view.spinner.adapter = BrigadesSpinnerAdapter(context!!)
        }
        if (student != null) {
            view.nameTextView.setText(student.name)
            view.lastNameTextView.setText(student.lastName)
            view.checkbox.isChecked = student.isRepentant
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addStudentButton.setOnClickListener {
            if (student == null) {
                val name = nameTextView.text.toString()
                val lastName = lastNameTextView.text.toString()
                val group = spinner.selectedItem as Brigade
                val isRepentant = checkbox.isChecked

                listener.onAddStudentInteraction(
                    Student(name, lastName, group.id, isRepentant),
                    false
                )
            } else {
                student.name = nameTextView.text.toString()
                student.lastName = lastNameTextView.text.toString()
                student.isRepentant = checkbox.isChecked
                listener.onAddStudentInteraction(student, true)
            }

            this@AddStudentBottomSheetDialog.dismiss()
        }
    }

    companion object {
        const val TAG = "ADD_STUDENT_BOTTOM_SHEET_DIALOG"

        fun newInstance(listener: AddStudentListener, value: List<Brigade>): AddStudentBottomSheetDialog {
            return AddStudentBottomSheetDialog(listener, value)
        }

        fun newInstance(listener: AddStudentListener): AddStudentBottomSheetDialog {
            return AddStudentBottomSheetDialog(listener)
        }

        fun newInstance(listener: AddStudentListener, studentP: Student): AddStudentBottomSheetDialog {
            return AddStudentBottomSheetDialog(
                listener,
                student = studentP
            )
        }
    }

    inner class BrigadesSpinnerAdapter(private val context: Context) : SpinnerAdapter {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            brigades?.let {
                view.brigadeNameTextView.text = it[position].name
            }
            return view
        }

        override fun isEmpty(): Boolean {
            return brigades?.isEmpty() ?: true
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            brigades?.let {
                view.brigadeNameTextView.text = it[position].name
            }
            return view
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun getItem(position: Int): Brigade? {
            return brigades?.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return brigades?.count() ?: 0
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver?) {

        }

        override fun registerDataSetObserver(observer: DataSetObserver?) {

        }

        override fun getViewTypeCount(): Int {
            return 1
        }
    }
}