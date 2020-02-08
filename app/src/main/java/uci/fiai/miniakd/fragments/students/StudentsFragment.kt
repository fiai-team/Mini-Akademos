package uci.fiai.miniakd.fragments.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragment_students.*
import kotlinx.android.synthetic.main.layout_empty.view.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.dialogs.MaterialDialogManager
import uci.fiai.miniakd.dialogs.addstudent.AddStudentBottomSheetDialog
import uci.fiai.miniakd.fragments.students.inner.StudentsByBrigadeListFragment
import uci.fiai.miniakd.utils.snackBar

class StudentsFragment : Fragment(), SpeedDialView.OnActionSelectedListener, AddStudentBottomSheetDialog.AddStudentListener, TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {

    private lateinit var viewModel: StudentsFragmentViewModel
    private var currentIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(StudentsFragmentViewModel::class.java)
        val root =  inflater.inflate(R.layout.fragment_students, container, false) as RelativeLayout

        val speedDialView = root.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(R.menu.students_speeddial)
        speedDialView.setOnActionSelectedListener(this)

        with(root.findViewById<TabLayout>(R.id.brigadesTabLayout)) {
            this.setupWithViewPager(root.findViewById(R.id.viewPager))
            this.addOnTabSelectedListener(this@StudentsFragment)
        }
        viewModel.brigadesList.observe(this, Observer { it ->
            if (it.isEmpty()) {
                toggleTabsUiVisibility(false)
                val emptyFragment = inflater.inflate(R.layout.layout_empty, root, false)
                emptyFragment.emptyDescriptionTextView.text = getString(R.string.empty_brigades)
                root.addView(emptyFragment)
            } else {
                toggleTabsUiVisibility(true)
                val emptyLayout =  root.findViewById<RelativeLayout>(R.id.emptyLayoutRoot)
                view?.findViewById<RelativeLayout>(R.id.studentsFragmentParentLayout)?.removeView(emptyLayout)
                fragmentManager?.let {
                    val adapter = FragmentViewPagerAdapter(it)
                    viewPager.adapter = adapter
                }
            }
        })
        return root
    }

    override fun onTabReselected(p0: TabLayout.Tab?) {
    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if (!speedDial.isShown)
            speedDial.show()
        tab?.let {
            currentIndex = it.position
        }
    }

    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        when(actionItem?.id) {
            R.id.addStudentItem -> {
                if (viewModel.canAddStudent() && fragmentManager != null) {
                    AddStudentBottomSheetDialog.newInstance(this, viewModel.brigadesList.value!!).show(fragmentManager!!, AddStudentBottomSheetDialog.TAG)
                }
                else {
                    showDialog()
                }
            }
            R.id.addBrigadeItem -> {
                showDialogAddBrigade()
            }
        }
        return false
    }

    override fun onAddStudentInteraction(student: Student, isEditionOperation: Boolean) {
        if (!isEditionOperation) {
            viewModel.addStudent(student)
        }
    }

    private fun toggleTabsUiVisibility(show: Boolean) {
        if (show) {
            brigadesTabLayout.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
        } else {
            brigadesTabLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
        }
    }
    private fun showDialog() {
        activity?.let {
            MaterialDialogManager(it).showDialogNedAddBrigade {
                showDialogAddBrigade()
            }
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

    inner class FragmentViewPagerAdapter (fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): StudentsByBrigadeListFragment {
            val fragment = StudentsByBrigadeListFragment()
            val arguments = Bundle()
            arguments.putInt(StudentsByBrigadeListFragment.BRIGADE_ARG, viewModel.brigadesList.value!![position].id)
            fragment.arguments = arguments
            return fragment
        }

        override fun getCount(): Int = viewModel.brigadesList.value!!.size

        override fun getPageTitle(position: Int): CharSequence = viewModel.brigadesList.value!![position].name
    }
}