package uci.fiai.miniakd.fragments.students

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.tabs.TabLayout
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.fragment_empty.*
import kotlinx.android.synthetic.main.fragment_empty.view.*
import kotlinx.android.synthetic.main.fragment_students.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Student
import uci.fiai.miniakd.dialogs.addstudent.AddStudentBottomSheetDialog
import uci.fiai.miniakd.fragments.students.inner.StudentsByBrigadeListFragment
import uci.fiai.miniakd.utils.snackBar

class StudentsFragment : Fragment(), SpeedDialView.OnActionSelectedListener, AddStudentBottomSheetDialog.AddStudentListener {

    private lateinit var viewModel: StudentsFragmentViewModel
    private var currentIndex = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(StudentsFragmentViewModel::class.java)
        val root =  inflater.inflate(R.layout.fragment_students, container, false)

        val speedDialView = root.findViewById<SpeedDialView>(R.id.speedDial)
        speedDialView.inflate(R.menu.students_speeddial)
        speedDialView.setOnActionSelectedListener(this)

        val tabListener = object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                if (!speedDialView.isShown) speedDialView.show()
                currentIndex = tab.position
            }
        }

        with(root.findViewById<TabLayout>(R.id.brigadesTabLayout)) {
            this.setupWithViewPager(root.findViewById(R.id.viewPager))
            this.addOnTabSelectedListener(tabListener)
        }

        viewModel.studentsList.observe(this, Observer {
            if (it.isEmpty()) {
                val emptyFragment = inflater.inflate(R.layout.fragment_empty, (root as RelativeLayout), false)
                emptyFragment.emptyDescriptionTextView.text = getString(R.string.empty_students)
                root.addView(emptyFragment)
            } else {
                (root as RelativeLayout).removeView(emptyLayoutRoot)
            }
        })
        viewModel.brigadesList.observe(this, Observer { it ->
            if (it.isEmpty()) {
                toggleTabsUiVisibility(false)
            } else {
                toggleTabsUiVisibility(true)
                fragmentManager?.let {
                    val adapter = FragmentViewPagerAdapter(it)
                    viewPager.adapter = adapter
                }
            }
        })

        return root
    }

    override fun onAddStudent(student: Student) {
        viewModel.addStudent(student)
    }

    private fun toggleTabsUiVisibility(show: Boolean) {
        if (show) {
            brigadesTabLayout.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
        }
        else{
            brigadesTabLayout.visibility = View.GONE
            viewPager.visibility = View.GONE
        }
    }

    private fun showDialog() {
        activity?.let {
            MaterialDialog.Builder(context!!)
                .title(R.string.string_add_student)
                .iconRes(R.drawable.ic_person_add)
                .iconAttr(R.attr.dialogIcon)
                .cancelable(false)
                .content(R.string.string_add_group_first)
                .positiveText(R.string.positive_string)
                .negativeText(R.string.negative)
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimaryDark)
                .onPositive { _: MaterialDialog, _: DialogAction -> showDialogAddBrigade() }
                .show()
        }
    }

    private fun showDialogAddBrigade() {
        activity?.let {
            MaterialDialog.Builder(it)
                .title(R.string.string_add_group)
                .iconRes(R.drawable.ic_people)
                .cancelable(false)
                .positiveText(R.string.string_add)
                .negativeText(R.string.string_cancel)
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimaryDark)
                .input("Nombre de la brigada", "", false) { _, input ->
                    viewModel.addBrigade(input.toString())
                    view?.let { v ->
                        snackBar(v, "Brigada $input agregada", false)
                    }
                }
                .inputRange(4, 10)
                .inputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                .contentColorRes(R.color.colorPrimaryDark)
                .show()
        }
    }


    override fun onActionSelected(actionItem: SpeedDialActionItem?): Boolean {
        if (actionItem != null) {
            when(actionItem.id) {
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
        else
            return false
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