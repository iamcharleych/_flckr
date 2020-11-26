package com.ic.logger.helper

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.ic.logger.Logger

internal object AppLifecycleLogger : LifecycleObserver {

    private val L = Logger()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        L.verb { "onMoveToForeground()" }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        L.verb { "onMoveToBackground()" }
    }
}
