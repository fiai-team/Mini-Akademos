package uci.fiai.miniakd.fragments.subjects

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragment_subjects.*
import kotlinx.android.synthetic.main.recyclerviewitem_subject.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.dialogs.subject.SubjectBottomSheetDialog
import uci.fiai.miniakd.extensions.*

class SubjectsFragment : Fragment(), SpeedDialView.OnActionSelectedListener, SubjectBottomSheetDialog.AddSubjectListener {

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

        viewModel.subjectsList.observe(this, Observer {
            if (it.isEmpty()) {

            }
            else {
                context?.let { context -> recyclerView.adapter = SubjectsAdapter(context, it) }
            }
        })

    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        when (actionItem?.id) {
            R.id.addSubjectItem -> {
                fragmentManager?.let {
                    SubjectBottomSheetDialog.newInstance(this@SubjectsFragment).show(it, SubjectBottomSheetDialog.TAG)
                }
            }
        }
        return false
    }

    override fun onAddSubjectInteraction(subject: Subject, isEditionOperation: Boolean) {
        if (isEditionOperation)
            viewModel.updateSubject(subject)
        else
            viewModel.insertSubject(subject)
    }

    private fun onStudentItemLongInteraction(subject: Subject, position: Int): Boolean {
        context?.let {
            MaterialDialog.Builder(it)
                .title(subject.name)
                .iconRes(R.drawable.ic_edit)
                .content("¿Qué desea hacer con la asignatura?")
                .contentColor(getPrimaryColor(it))
                .positiveText(R.string.string_edit)
                .negativeText(R.string.string_remove)
                .positiveColor(getAccentColor(it))
                .negativeColor(getPrimaryColor(it))
                .onPositive { _, _ -> showDialogEditSubject(subject) }
                .onNegative { _, _ -> showDialogDeleteSubject(subject, position) }
                .show()
        }
        return true
    }

    private fun showDialogEditSubject(subject: Subject) {
        fragmentManager?.let {
            SubjectBottomSheetDialog.newInstance(this, subject).show(it, SubjectBottomSheetDialog.TAG)
        }
    }

    private fun showDialogDeleteSubject(subject: Subject, position: Int) {
        activity?.let {
            MaterialDialog.Builder(it)
                .title("${getString(R.string.string_delete_course)} ${subject.name}")
                .content("¿Desea eliminar la asignatura \"${subject.name}\"? También eliminará los turnos asociados.")
                .contentColor(getPrimaryColor(it))
                .positiveText(R.string.string_delete_course)
                .negativeText(R.string.string_no_delete)
                .positiveColor(getAccentColor(it))
                .negativeColor(getPrimaryColor(it))
                .onPositive { _, _ ->
                    var canDelete = true
                    val action = View.OnClickListener {
                        (recyclerView.adapter as SubjectsAdapter).restoreItem(subject, position)
                        canDelete = false
                    }
                    val callback = object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)

                            if (canDelete)
                                viewModel.removeSubject(subject)
                        }
                    }

                    recyclerView.adapter?.let { adapter ->
                        (adapter as SubjectsAdapter).removeItem(position)
                    }
                    showUndoSnackbar(getString(R.string.deletedSubjectMessage), action, callback)
                }
                .show()
        } ?: recyclerView.adapter?.notifyItemChanged(position)
    }

    inner class SubjectsAdapter(private val context: Context, private var subjects: ArrayList<Subject>) : RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.recyclerviewitem_subject, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return subjects.count()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.nameTextView.text = subjects[position].name
            holder.progressBar.max = subjects[position].hoursCount
            holder.progressBar.progress = subjects[position].doneHoursCount

            holder.itemView.setOnLongClickListener {
                this@SubjectsFragment.onStudentItemLongInteraction(subjects[position], position)
            }
        }

        fun removeItem(position: Int) {
            subjects.remove(subjects[position])
            notifyItemRemoved(position)
        }

        fun restoreItem(subject: Subject, position: Int) {
            subjects.add(position, subject)
            notifyItemInserted(position)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.nameTextView
            val progressBar: ProgressBar = itemView.progressBar
        }
    }

}
