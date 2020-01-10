package uci.fiai.miniakd.fragments.students.inner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_studentsbybrigade.*
import kotlinx.android.synthetic.main.recyclerviewitem_studentsbybrigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.dialogs.addstudent.AddStudentBottomSheetDialog
import uci.fiai.miniakd.extensions.showUndoSnackbar

class StudentsByBrigadeListFragment : Fragment(), AddStudentBottomSheetDialog.AddStudentListener {

    private lateinit var viewModel: StudentsByBrigadeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(StudentsByBrigadeViewModel::class.java)
        viewModel.init(arguments!!.getInt(BRIGADE_ARG, 0))
        val root =  inflater.inflate(R.layout.fragment_studentsbybrigade, container, false)

        root.findViewById<TextView>(R.id.emptyDescriptionTextView).text = getString(R.string.emptyStudentsByBrigade)

        viewModel.studentsList.observe(this, Observer {
            if (it.isEmpty()) {
                recyclerView.visibility = View.GONE
                emptyLayoutInclude.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                emptyLayoutInclude.visibility = View.GONE
                recyclerView.adapter = StudentsByBrigadeAdapter(context!!, it)
            }
        })
        return root
    }

    override fun onAddStudentInteraction(student: Student, isEditionOperation: Boolean) {
        if (isEditionOperation) {
            viewModel.saveEditStudent(student)
        }
    }

    fun onStudentItemLongInteraction(obj: Student, position: Int): Boolean {
        activity?.let {
            MaterialDialog.Builder(it)
                .title(obj.name + " " + obj.lastName)
                .iconRes(R.drawable.ic_edit)
                .cancelable(false)
                .content("¿Qué desea hacer con el estudiante?")
                .contentColorRes(R.color.colorPrimary)
                .positiveText(R.string.string_edit)
                .negativeText(R.string.string_remove)
                .neutralText("Nada")
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorPrimary)
                .neutralColorRes(R.color.colorPrimaryDark)
                .onPositive { _, _ -> showDialogEditStudent(obj) }
                .onNegative { _, _ -> showDialogDeleteStudent(obj, position) }
                .show()
        }
        return true

    }

    private fun showDialogEditStudent(obj: Student) {
        AddStudentBottomSheetDialog.newInstance(this, obj).show(fragmentManager!!, AddStudentBottomSheetDialog.TAG)
    }

    private fun showDialogDeleteStudent(student: Student, position: Int) {
        activity?.let { it ->
            MaterialDialog.Builder(it)
                .title(R.string.string_delete_student)
                .iconRes(R.drawable.ic_delete)
                .cancelable(false)
                .content("¿Seguro que quiere eliminar al estudiante ${student.name} ${student.lastName}?")
                .contentColorRes(R.color.colorPrimary)
                .positiveText(R.string.string_yes)
                .negativeText(R.string.string_no_delete)
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorPrimaryDark)
                .onPositive { _, _ ->
                    var canDelete = true
                    val action = View.OnClickListener {
                        recyclerView.adapter?.let { adapter ->
                            (adapter as StudentsByBrigadeAdapter).restoreItem(student, position)
                        }
                    }
                    val callback = object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)

                            if (canDelete) viewModel.removeStudent(student)
                        }
                    }

                    recyclerView.adapter?.let { adapter ->
                        (adapter as StudentsByBrigadeAdapter).removeItem(position);
                    }
                    showUndoSnackbar("Estudiante eliminado", action, callback)

                }
                .show()
        } ?: recyclerView.adapter?.notifyItemChanged(position)
    }

    inner class StudentsByBrigadeAdapter(private val context: Context, private var students: ArrayList<Student>) : RecyclerView.Adapter<StudentsByBrigadeAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.recyclerviewitem_studentsbybrigade, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return students.count()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.nameTextView.text = "${students[position].name} ${students[position].lastName}"

            holder.itemView.setOnLongClickListener {
                this@StudentsByBrigadeListFragment.onStudentItemLongInteraction(students[position], position)
            }
        }

        fun removeItem(position: Int) {
            students.remove(students[position])
            notifyItemRemoved(position)
        }

        fun restoreItem(student: Student, position: Int) {
            students.add(position, student)
            notifyItemInserted(position)
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.nameTextView
        }
    }

    companion object {
        const val BRIGADE_ARG = "BRIGADE_ARG"
    }
}