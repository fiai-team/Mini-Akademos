package uci.fiai.miniakd.dialogs

import android.content.Context
import android.text.InputType
import android.view.View
import androidx.annotation.StringRes
import com.afollestad.materialdialogs.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_subjects.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.MainDatabase
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.database.entities.Subject
import uci.fiai.miniakd.extensions.*
import uci.fiai.miniakd.fragments.subjects.SubjectsFragment

class MaterialDialogManager(private val context: Context) {

    fun showDialogAddBrigade(onInputCallback: (dialog: MaterialDialog, input: CharSequence) -> Unit) {
        MaterialDialog.Builder(context)
            .title(R.string.string_add_group)
            .iconRes(R.drawable.ic_people)
            .positiveText(R.string.string_add)
            .negativeText(R.string.string_cancel)
            .positiveColorRes(R.color.colorPrimary)
            .negativeColorRes(R.color.colorPrimaryDark)
            .input("Nombre de la brigada", "", false) { dialog, input ->
                onInputCallback(dialog, input)
            }
            .inputRange(4, 10)
            .inputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
            .contentColorRes(R.color.colorPrimaryDark)
            .show()
    }

    fun showDialogNedAddBrigade(onCallBack: () -> Unit) {
        MaterialDialog.Builder(context)
            .title(R.string.string_add_student)
            .iconRes(R.drawable.ic_person_add)
            .iconAttr(R.attr.dialogIcon)
            .content(R.string.string_add_group_first)
            .positiveText(R.string.positive_string)
            .negativeText(R.string.negative)
            .positiveColorRes(R.color.colorPrimary)
            .negativeColorRes(R.color.colorPrimaryDark)
            .onPositive { _: MaterialDialog, _: DialogAction ->
                onCallBack()
            }
            .show()
    }

    fun showRemoveBrigadeDialog(brigade: Brigade, onCallBack: () -> Unit) {
        MaterialDialog.Builder(context)
            .title(context.getString(R.string.string_delete_group_with_name, brigade.name))
            .content(R.string.string_delete_group_text)
            .contentColor(getPrimaryColor(context))
            .positiveText(R.string.string_delete_group)
            .negativeText(R.string.string_no_delete)
            .positiveColor(getAccentColor(context))
            .negativeColorRes(R.color.colorPrimaryDark)
            .onPositive { _, _ ->
                onCallBack()
            }
            .show()
    }

    fun showSubjectOptionsDialog(subject: Subject, position: Int, onPositive: (subject: Subject) -> Unit, onNegative: (subject: Subject, position: Int) -> Unit) {
        MaterialDialog.Builder(context)
            .title(subject.name)
            .iconRes(R.drawable.ic_edit)
            .content("¿Qué desea hacer con la asignatura?")
            .contentColor(getPrimaryColor(context))
            .positiveText(R.string.string_edit)
            .negativeText(R.string.string_remove)
            .positiveColor(getAccentColor(context))
            .negativeColor(getPrimaryColor(context))
            .onPositive { _, _ -> onPositive(subject) }
            .onNegative { _, _ -> onNegative(subject, position) }
            .show()
    }
    fun showSubjectDeleteConfirmationDialog(subject: Subject, onCallBack: () -> Unit) {
        MaterialDialog.Builder(context)
            .title("${getString(R.string.string_delete_course)} ${subject.name}")
            .content("¿Desea eliminar la asignatura \"${subject.name}\"? También eliminará los turnos asociados.")
            .contentColor(getPrimaryColor(context))
            .positiveText(R.string.string_delete_course)
            .negativeText(R.string.string_no_delete)
            .positiveColor(getAccentColor(context))
            .negativeColor(getPrimaryColor(context))
            .onPositive { _, _ ->
                onCallBack()
            }
            .show()
    }


    fun getString(@StringRes resId: Int) {
        context.getString(resId)
    }

    fun showBrigadeOptionsialog(brigade: Brigade, onPositiveCallback: () -> Unit, onNegativeCallback: () -> Unit) {
        MaterialDialog.Builder(context)
            .title("Brigada ${brigade.name}")
            .iconRes(R.drawable.ic_edit)
            .content("¿Qué desea hacer con la brigada?")
            .contentColorRes(R.color.colorPrimary)
            .positiveText(R.string.string_edit)
            .negativeText(R.string.string_remove)
            .positiveColorRes(R.color.colorAccent)
            .negativeColorRes(R.color.colorPrimary)
            .onPositive { _, _ ->
                onPositiveCallback()
            }
            .onNegative { _, _ ->
                onNegativeCallback()
            }
            .show()
    }

    fun showEditBrigadeDialog(brigade: Brigade, onInputCallback: (brigade: Brigade, input: CharSequence) -> Unit) {
        MaterialDialog.Builder(context)
            .title(R.string.string_edit_group)
            .iconRes(R.drawable.ic_edit)
            .contentColorRes(R.color.colorPrimaryDark)
            .positiveText(R.string.string_edit)
            .negativeText(R.string.string_cancel)
            .positiveColorRes(R.color.colorAccent)
            .negativeColorRes(R.color.colorPrimaryDark)
            .input("Nombre de la brigada",  brigade.name, false) { _, input ->
                onInputCallback(brigade, input)

            }
            .inputRange(4, 10)
            .inputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
            .show()
    }

    fun showRemoveBrigadeDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}