package uci.fiai.miniakd.dialogs.turns

import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheetdialog_addturn.view.*
import kotlinx.android.synthetic.main.spinneritem_brigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.database.entities.Turn
import uci.fiai.miniakd.database.enums.LocalType
import uci.fiai.miniakd.database.enums.Places
import uci.fiai.miniakd.database.enums.Typology
import uci.fiai.miniakd.extensions.dayMonth
import uci.fiai.miniakd.extensions.getMonth
import uci.fiai.miniakd.extensions.getYear
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

class TurnsBottomSheetDialog(private var listener: AddTurnListener, private val turn: Turn?, private val brigades: ArrayList<Brigade>, private val subjects: ArrayList<Subject>, private val date: Calendar) : BottomSheetDialogFragment(){

    interface AddTurnListener {
        fun onAddTurnInteraction(turn: Turn?, isEditionOperation: Boolean)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheetdialog_addturn, container, false)
        context?.let {
            view.brigadesSpinner.adapter = BrigadesSpinnerAdapter(it)
            view.subjectsSpinner.adapter = SubjectSpinnerAdapter(it)
        }
        view.addTurnButton.setOnClickListener {
            val turn = Turn()
            with(view) {
                turn.brigadeId = (brigadesSpinner.selectedItem as Brigade).id
                turn.subjectId = (subjectsSpinner.selectedItem as Subject).id
                turn.local = localEditText.text.toString()
                turn.place = Places.of(placeSpinner.selectedItemPosition)
                turn.localType = LocalType.of(localTypeSpinner.selectedItemPosition)
                turn.typology = Typology.of(typologySpinner.selectedItemPosition)
                turn.timeTurn = turnTimeSpinner.selectedItemPosition
                turn.day = date.dayMonth()
                turn.month = date.getMonth()
                turn.year = date.getYear()
            }
            listener.onAddTurnInteraction(turn, false)
            dismiss()
        }
        return view
    }

    inner class BrigadesSpinnerAdapter(private val context: Context) : SpinnerAdapter {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            brigades.let {
                view.brigadeNameTextView.text = it[position].name
            }
            return view
        }

        override fun isEmpty(): Boolean {
            return brigades.isEmpty() ?: true
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            brigades.let {
                view.brigadeNameTextView.text = it[position].name
            }
            return view
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun getItem(position: Int): Brigade? {
            return brigades.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return brigades.count() ?: 0
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

    inner class SubjectSpinnerAdapter(private val context: Context) : SpinnerAdapter {

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            subjects.let {
                view.brigadeNameTextView.text = it[position].name
            }
            return view
        }

        override fun isEmpty(): Boolean {
            return subjects.isEmpty() ?: true
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view =  if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                inflater.inflate(R.layout.spinneritem_brigade, parent, false)
            } else
                convertView

            subjects.let {
                view.brigadeNameTextView.text = it[position].name
            }
            return view
        }

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun getItem(position: Int): Subject? {
            return subjects.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return subjects.count() ?: 0
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

    companion object {
        const val TAG = "ADD_TURN_BOTTOM_SHEET_DIALOG"

        fun newInstance(listener: TurnsBottomSheetDialog.AddTurnListener, brigades: ArrayList<Brigade>, subjects: ArrayList<Subject>, date: Calendar): TurnsBottomSheetDialog {
            return TurnsBottomSheetDialog(listener, null, brigades, subjects, date)
        }

        fun newInstance(listener: TurnsBottomSheetDialog.AddTurnListener, turn: Turn, brigades: ArrayList<Brigade>, subjects: ArrayList<Subject>, date: Calendar): TurnsBottomSheetDialog {
            return TurnsBottomSheetDialog(listener, turn, brigades, subjects, date)
        }
    }



}