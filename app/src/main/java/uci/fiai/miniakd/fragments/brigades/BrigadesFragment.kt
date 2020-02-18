package uci.fiai.miniakd.fragments.brigades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragments_brigades.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.adapters.BrigadesRecyclerViewAdapter
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.dialogs.MaterialDialogManager
import uci.fiai.miniakd.extensions.*
import uci.fiai.miniakd.utils.snackBar

class BrigadesFragment : Fragment(), SpeedDialView.OnActionSelectedListener, BrigadesRecyclerViewAdapter.OnBrigadesRecyclerViewAdapterListener {

    private lateinit var viewModel: BrigadesFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        context?.apply {
            viewModel = BrigadesFragmentViewModel(this)
        }

        val root = inflater.inflate(R.layout.fragments_brigades, container, false)

        val speedDialView = root.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(R.menu.brigades_speeddial)
        speedDialView.setOnActionSelectedListener(this)

        root.findViewById<TextView>(R.id.emptyDescriptionTextView).setText(R.string.notRegisteredBrigadesText)

        viewModel.brigadesList.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                toggleUiVisibility(true)
            } else {
                toggleUiVisibility(false)
                context?.let { context ->
                    recyclerView.adapter = BrigadesRecyclerViewAdapter(context, this, it)
                }
            }
        })
        return root
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        when (actionItem?.id) {
            R.id.addBrigadeItem -> {
                showDialogAddBrigade()
            }
        }
        return false
    }

    override fun onBrigadeItemClick(brigade: Brigade, position: Int) {

    }


    override fun onBrigadeItemLongClick(brigade: Brigade, position: Int) {
        activity?.apply {
            MaterialDialogManager(this).showBrigadeOptionsialog(brigade, {
                MaterialDialogManager(this).showEditBrigadeDialog(brigade) { brigade: Brigade, input: CharSequence ->
                    val newName = input.toString().trimUpperCase()
                    brigade.name = newName

                        Thread {
                            MainDatabase.instance(this).brigades.update(brigade)
                            viewModel.update()
                            view?.let { v -> snackbar(v, "Brigada editada") }
                        }.start()
                    }
            }, {
                MaterialDialogManager(this).showRemoveBrigadeDialog(brigade) {
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
            })
        }
    }


    private fun showDialogAddBrigade() {
        activity?.let {
            MaterialDialogManager(it).showDialogAddBrigade { _, input ->
                viewModel.addBrigade(input.toString())
                view?.let { v ->
                    snackBar(v, "Brigada $input agregada", false)
                }
            }
        }
    }

    private fun toggleUiVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            recyclerView.visibility = View.GONE
            emptyLayoutInclude.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyLayoutInclude.visibility = View.GONE
        }
    }
}