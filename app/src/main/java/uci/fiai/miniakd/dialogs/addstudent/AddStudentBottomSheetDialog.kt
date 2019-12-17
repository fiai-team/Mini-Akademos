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
import kotlinx.android.synthetic.main.bottomsheetdialog_addstudent.view.*
import kotlinx.android.synthetic.main.spinneritem_brigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Student

class AddStudentBottomSheetDialog(private var listener: AddStudentListener, private val brigades: List<Brigade>) : BottomSheetDialogFragment() {

    interface AddStudentListener {
        fun onAddStudent(student: Student)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheetdialog_addstudent, container, false)
        view.spinner.adapter = BrigadesSpinnerAdapter(context!!, brigades)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addStudentButton.setOnClickListener {
            val name = nameTextView.text.toString()
            val lastName = lastNameTextView.text.toString()
            val group = spinner.selectedItem as Brigade
            val isRepentant = checkbox.isChecked

            listener.onAddStudent(Student(name, lastName, group.name, isRepentant))

            this@AddStudentBottomSheetDialog.dismiss()
        }
    }

    companion object {
        const val TAG = "ADD_STUDENT_BOTTOM_SHEET_DIALOG"

        fun newInstance(listener: AddStudentListener, value: List<Brigade>): AddStudentBottomSheetDialog {
            return AddStudentBottomSheetDialog(listener, value)
        }
    }

    inner class BrigadesSpinnerAdapter(private val context: Context, brigades: List<Brigade>) : SpinnerAdapter {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            view.brigadeNameTextView.text = brigades[position].name

            return view
        }

        override fun isEmpty(): Boolean {
            return brigades.isEmpty()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            view.brigadeNameTextView.text = brigades[position].name

            return view
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun getItem(position: Int): Any {
            return brigades[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return brigades.count()
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