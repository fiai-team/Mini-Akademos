package uci.fiai.miniakd.fragments.brigades

import android.content.Context
import android.os.Bundle
import android.text.InputType
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
import kotlinx.android.synthetic.main.fragments_brigades.*
import kotlinx.android.synthetic.main.recyclerviewitem_brigade.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.extensions.*

class BrigadesFragment : Fragment() {

    private lateinit var viewModel: BrigadesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(BrigadesFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragments_brigades, container, false)

        viewModel.brigadesList.observe(this, Observer {
            if (context != null)
                recyclerView.adapter = BrigadesRecyclerViewAdapter(context!!, it)
        })
        return root
    }

    fun onItemLongClickInteraction(brigade: Brigade, position: Int) {
        activity?.let {
            MaterialDialog.Builder(it)
                .title("Brigada ${brigade.name}")
                .iconRes(R.drawable.ic_edit)
                .content("¿Qué desea hacer con la brigada?")
                .contentColorRes(R.color.colorPrimary)
                .positiveText(R.string.string_edit)
                .negativeText(R.string.string_remove)
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorPrimary)
                .onPositive { _, _ -> showDialogEditGroup(brigade) }
                .onNegative { _, _ -> showDialogDeleteGroup(brigade, position) }
                .show()
        }
    }

    private fun showDialogEditGroup(brigade: Brigade) {
        activity?.let {
            MaterialDialog.Builder(it)
                .title(R.string.string_edit_group)
                .iconRes(R.drawable.ic_edit)
                .contentColorRes(R.color.colorPrimaryDark)
                .positiveText(R.string.string_edit)
                .negativeText(R.string.string_cancel)
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorPrimaryDark)
                .input("Nombre de la brigada",  brigade.name, false) { _, input ->
                    val newName = input.toString().trimUpperCase()
                    brigade.name = newName

                    context?.let {
                        Thread {
                            MainDatabase.instance(it).brigades.update(brigade)
                            viewModel.update()
                            view?.let { v -> snackbar(v, "Brigada editada") }
                        }.start()
                    }
                }
                .inputRange(4, 10)
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                .show()
        }
    }

    private fun showDialogDeleteGroup(brigade: Brigade, position: Int) {
        activity?.let {
            MaterialDialog.Builder(it)
                .title(getString(R.string.string_delete_group_with_name, brigade.name))
                .content(R.string.string_delete_group_text)
                .contentColor(getPrimaryColor(it))
                .positiveText(R.string.string_delete_group)
                .negativeText(R.string.string_no_delete)
                .positiveColor(getAccentColor(it))
                .negativeColorRes(R.color.colorPrimaryDark)
                .onPositive { _, _ ->
                    var canDelete = true
                    val action = View.OnClickListener {
                        recyclerView.adapter?.let { adapter ->
                            canDelete = false
                            (adapter as BrigadesRecyclerViewAdapter).restoreItem(brigade, position)
                        }
                    }
                    val callback = object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)

                            if (canDelete) viewModel.removeBrigade(brigade)
                        }
                    }

                    recyclerView.adapter?.let { adapter ->
                        (adapter as BrigadesRecyclerViewAdapter).removeItem(position);
                    }
                    showUndoSnackbar("Brigada eliminada", action, callback)
                }
                .show()
        }
    }

    inner class BrigadesRecyclerViewAdapter(private val context: Context, private val brigades: ArrayList<Brigade>) : RecyclerView.Adapter<BrigadesRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.recyclerviewitem_brigade, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnLongClickListener {
                this@BrigadesFragment.onItemLongClickInteraction(brigades[position], position)
                true
            }
            holder.nameTextView.text = brigades[position].name
            when (val studentsCount = brigades[position].studentsCount) {
                0 -> holder.countTextView.text = getString(R.string.students_count_empty)
                1 -> holder.countTextView.text = getString(R.string.student_count_singular, studentsCount)
                else -> holder.countTextView.text = getString(R.string.student_count_plural, studentsCount)
            }
        }

        override fun getItemCount(): Int {
            return brigades.count()
        }

        fun removeItem(position: Int) {
            brigades.remove(brigades[position])
            notifyItemRemoved(position)
        }

        fun restoreItem(brigade: Brigade, position: Int) {
            brigades.add(position, brigade)
            notifyItemInserted(position)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nameTextView: TextView = itemView.nameTextView
            val countTextView: TextView = itemView.countTextView
        }
    }
}