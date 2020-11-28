package com.ic.logger.helper

import android.content.ComponentCallbacks2
import android.content.res.Configuration
import com.ic.logger.Logger
import timber.log.Timber

internal object ComponentCallbacksLogger : ComponentCallbacks2 {

    private val L = Logger()

    override fun onLowMemory() {
        L.verb { "onLowMemory()" }
    }

    override fun onConfigurationChanged(p0: Configuration) {
        L.verb { "onConfigurationChanged(): $p0" }
    }

    override fun onTrimMemory(p0: Int) {
        L.verb { "onTrimMemory(): $p0" }
    }
}
