package com.ic.logger

class LoggerExceptionHandler(
    private val defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        L.error(e) { "uncaughtException(): thread=$t" }
        L.info { "BYE!" }
        Logger.close()

        defaultHandler?.uncaughtException(t, e)
    }

    companion object {
        private val L = Logger()
    }
}