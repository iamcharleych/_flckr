package com.vochi.logger.timber

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber
import kotlin.math.min

/**
 * Simple [Timber.Tree] that prints to log cat and uses manually set tag only,
 * as opposed to [Timber.DebugTree]
 */
@SuppressLint("LogNotTimber")
internal class LogCatTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (message.length < MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Log.wtf(tag, message)
            } else {
                Log.println(priority, tag, message)
            }
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part)
                } else {
                    Log.println(priority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    companion object {
        private const val MAX_LOG_LENGTH = 4000
    }
}