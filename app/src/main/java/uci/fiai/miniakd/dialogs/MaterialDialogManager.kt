package uci.fiai.miniakd.dialogs

import android.content.Context
import android.text.InputType
import com.afollestad.materialdialogs.*
import uci.fiai.miniakd.R
import uci.fiai.miniakd.database.entities.Brigade
import uci.fiai.miniakd.extensions.*

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

    fun showDialogDeleteBrigade(brigade: Brigade, onCallBack: () -> Unit) {
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
}