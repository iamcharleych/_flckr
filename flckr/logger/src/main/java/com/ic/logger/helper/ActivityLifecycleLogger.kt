package com.ic.logger.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.ic.logger.Logger

internal class ActivityLifecycleLogger(
    private val logFragmentLifecycle: Boolean = true
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        L.verb { "onActivityCreated(): $p0, bundle=$p1" }

        if (logFragmentLifecycle && p0 is FragmentActivity) {
            p0.supportFragmentManager.registerFragmentLifecycleCallbacks(FragmentLifecycleLogger, true)
        }
    }

    override fun onActivityStarted(p0: Activity) {
        L.verb { "onActivityStarted(): $p0" }
    }

    override fun onActivityResumed(p0: Activity) {
        L.verb { "onActivityResumed(): $p0" }
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        L.verb { "onActivitySaveInstanceState(): $p0, bundle=$p1" }
    }

    override fun onActivityPaused(p0: Activity) {
        L.verb { "onActivityPaused(): $p0" }
    }

    override fun onActivityStopped(p0: Activity) {
        L.verb { "onActivityStopped(): $p0" }
    }

    override fun onActivityDestroyed(p0: Activity) {
        L.verb { "onActivityDestroyed(): $p0" }
    }

    companion object {
        private val L = Logger()
    }
}
