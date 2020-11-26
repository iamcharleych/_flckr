package com.ic.logger.helper

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ic.logger.Logger
import timber.log.Timber

internal object FragmentLifecycleLogger : FragmentManager.FragmentLifecycleCallbacks() {

    private val L = Logger()
    
    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        L.verb { "onFragmentViewCreated(): $f" }
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentStopped(): $f" }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        L.verb { "onFragmentCreated(): $f" }
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentResumed(): $f" }
    }

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        L.verb { "onFragmentAttached(): $f" }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentDestroyed(): $f" }
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        L.verb { "onFragmentSaveInstanceState(): $f" }
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentStarted(): $f" }
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentViewDestroyed(): $f" }
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        L.verb { "onFragmentActivityCreated(): $f" }
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentPaused(): $f" }
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        L.verb { "onFragmentDetached(): $f" }
    }
}
