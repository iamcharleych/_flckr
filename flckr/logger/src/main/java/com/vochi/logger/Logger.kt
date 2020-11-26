package com.ic.logger

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.ic.logger.helper.ActivityLifecycleLogger
import com.ic.logger.helper.AppLifecycleLogger
import com.ic.logger.helper.ComponentCallbacksLogger
import com.ic.logger.timber.FileTree
import com.ic.logger.timber.LogCatTree
import timber.log.Timber
import java.io.File

class Logger private constructor(val tag: String, val showThreadName: Boolean) {

    inline fun verb(message: () -> String) = log(Log.VERBOSE, tag, null, message)

    inline fun debug(message: () -> String) = log(Log.DEBUG, tag, null, message)

    inline fun info(message: () -> String) = log(Log.INFO, tag, null, message)

    inline fun warn(message: () -> String) = log(Log.WARN, tag, null, message)

    inline fun warn(tr: Throwable? = null, message: () -> String) = log(Log.WARN, tag, tr, message)

    inline fun error(message: () -> String) = log(Log.ERROR, tag, null, message)

    inline fun error(tr: Throwable? = null, message: () -> String) = log(Log.ERROR, tag, tr, message)

    inline fun log(priority: Int, tag: String, tr: Throwable?, message: () -> String) {
        if (priority < logLevel) return
        // In combination with proguard, condition on statically resolved BuildConfig flag removes `StringBuilder`
        // instances when string interpolation is used in message block
        if (BuildConfig.DEBUG || BuildConfig.SUPPORT) {
            val msg = if (showThreadName) "[${Thread.currentThread().name}] ${message()}" else message()
            Timber.tag(tag)
            Timber.log(priority, tr, msg)
        }
    }

    data class Config(
        val exceptionHandlerEnabled: Boolean = false,
        val logCatEnabled: Boolean = false,
        val fileLoggerConfig: FileLoggerConfig = FileLoggerConfig(),
        val showThreadName: Boolean = false,
        val logActivityLifecycle: Boolean = false,
        val logFragmentLifecycle: Boolean = false,
        val logComponentCallbacks: Boolean = false,
        val logProcessLifecycle: Boolean = false
    )

    data class FileLoggerConfig(
        val fileLogsEnabled: Boolean = false,
        val fileLogsDirectory: File? = null
    )

    companion object {
        private const val MAX_TAG_LENGTH = 23
        private val ANONYMOUS_CLASS = Regex("(\\$\\d+)+$")

        internal var config: Config = Config()
            private set

        var logLevel: Int = Log.VERBOSE

        fun init(app: Application, config: Config = Config()) {
            this.config = config

            if (config.exceptionHandlerEnabled) {
                Thread.setDefaultUncaughtExceptionHandler(
                    LoggerExceptionHandler(Thread.getDefaultUncaughtExceptionHandler())
                )
            }

            config.run {
                if (logCatEnabled) {
                    Timber.plant(LogCatTree())
                }

                if (fileLoggerConfig.fileLogsEnabled) {
                    fileLoggerConfig.fileLogsDirectory?.let {
                        Timber.plant(FileTree(it.absolutePath))
                    }
                }

                if (logActivityLifecycle) {
                    app.registerActivityLifecycleCallbacks(ActivityLifecycleLogger(logFragmentLifecycle))
                }

                if (logComponentCallbacks) {
                    app.registerComponentCallbacks(ComponentCallbacksLogger)
                }

                if (logProcessLifecycle) {
                    ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleLogger)
                }
            }
        }

        operator fun invoke(tag: String = createTag()): Logger {
            return Logger(tag, config.showThreadName)
        }

        private fun createTag(): String {
            val stackTrace = Throwable().stackTrace
            val element = stackTrace.first { it.className != Companion::class.java.name }
            return createStackElementTag(element) ?: ""
        }

        private fun createStackElementTag(element: StackTraceElement): String? {
            val tag = ANONYMOUS_CLASS.replace(element.className.substringAfterLast('.'), "")

            // Tag length limit was removed in API 24.
            return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tag
            } else {
                tag.substring(0, MAX_TAG_LENGTH)
            }
        }
    }
}
