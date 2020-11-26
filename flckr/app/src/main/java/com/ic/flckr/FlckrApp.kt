package com.ic.flckr

import android.app.Application
import android.os.Build
import com.ic.logger.Logger

class FlckrApp : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLogger()
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Logger.init(this, Logger.Config(
                exceptionHandlerEnabled = true,
                logCatEnabled = true,
                fileLoggerConfig = Logger.FileLoggerConfig(
                    fileLogsEnabled = true,
                    fileLogsDirectory = getExternalFilesDir("logs")
                ),
                showThreadName = true,
                logActivityLifecycle = true,
                logFragmentLifecycle = true,
                logComponentCallbacks = true,
                logProcessLifecycle = true
            ))

            System.setProperty("kotlinx.coroutines.debug", "on")

            L.info { """
                HELLO!
                Android OS: API ${Build.VERSION.SDK_INT}
                Device: ${Build.MANUFACTURER} ${Build.MODEL} (${Build.DEVICE})
                Supported ABIs: ${Build.SUPPORTED_ABIS.toList()}
                ${resources.displayMetrics}
            """.trimIndent() }
        }
    }

    companion object {
        private val L = Logger()
    }
}