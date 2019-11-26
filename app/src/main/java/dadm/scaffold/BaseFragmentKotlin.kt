package dadm.scaffold

import android.support.v4.app.Fragment


open class BaseFragmentK : Fragment() {
    open fun onBackPressed(): Boolean {
        return false
    }
}
