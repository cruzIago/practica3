package dadm.scaffold

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import dadm.scaffold.counter.GameFragmentK
import dadm.scaffold.counter.MainMenuFragmentK

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scaffold)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MainMenuFragmentK(), TAG_FRAGMENT)
                    .commit()
        }
    }

    fun startGame() {
        // Navigate the the game fragment, which makes the start automatically
        navigateToFragment(GameFragmentK())
    }

    private fun navigateToFragment(dst: BaseFragmentK) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, dst, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(TAG_FRAGMENT) as BaseFragmentK?
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    fun navigateBack() {
        // Do a push on the navigation history
        super.onBackPressed()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val decorView = window.decorView
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LOW_PROFILE)
            } else {
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
        }
    }

    companion object {

        private val TAG_FRAGMENT = "content"
    }
}
