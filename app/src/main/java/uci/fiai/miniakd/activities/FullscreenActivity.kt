package uci.fiai.miniakd.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_fullscreen.*
import uci.fiai.miniakd.R
import java.util.*
import kotlin.concurrent.schedule


class FullscreenActivity : AppCompatActivity()/*, OnStateChangeListener*/ {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fullscreenCoordinatorLayout.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        versionTextView.let {
            try {
                val packageInfo = packageManager.getPackageInfo(packageName, 0)
                it.append(packageInfo.versionName)
            } catch (e: PackageManager.NameNotFoundException) {
                it.visibility = GONE
            }
        }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)


        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        appNameTextView.animation = animation
        logoImageView.animation = animation
        versionTextView.animation = animation


        Timer().schedule(3500) {
            showNextActivity()
        }
    }

    private fun showNextActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
