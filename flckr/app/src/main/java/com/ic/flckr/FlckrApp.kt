package com.ic.flckr

import android.app.Application
import android.os.Build
import com.ic.flckr.common.di.DaggerAppComponent
import com.ic.flckr.utils.Reachability
import com.ic.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class FlckrApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        Reachability.init(this)
        setupLogger()
        setupDagger()
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

    private fun setupDagger() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    companion object {
        private val L = Logger()
    }
}