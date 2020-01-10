package uci.fiai.miniakd.extensions

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Fragment.showUndoSnackbar(text: String, action: View.OnClickListener?, callback: BaseTransientBottomBar.BaseCallback<Snackbar>?) {
    this.view?.let { view ->
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        snackbar.setAction("Deshacer", action)
        callback?.let { callback ->
            snackbar.addCallback(callback)
        }
        snackbar.show()
    }
}