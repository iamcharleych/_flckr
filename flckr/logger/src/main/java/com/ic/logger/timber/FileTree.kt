package com.ic.logger.timber

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber
import java.io.Closeable
import java.io.Flushable
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.*
import java.util.logging.Formatter
import java.util.logging.Logger

private val formatter = SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.US)

private fun priorityToLetter(priority: Int): String {
    return when (priority) {
        Log.VERBOSE -> "V"
        Log.DEBUG -> "D"
        Log.INFO -> "I"
        Log.WARN -> "W"
        Log.ERROR -> "E"
        Log.ASSERT -> "A"
        else -> "?"
    }
}

private fun priorityToLevel(priority: Int): Level {
    return when (priority) {
        Log.VERBOSE -> Level.FINER
        Log.DEBUG -> Level.FINE
        Log.INFO -> Level.INFO
        Log.WARN -> Level.WARNING
        Log.ERROR -> Level.SEVERE
        Log.ASSERT -> Level.SEVERE
        else -> Level.OFF
    }
}

@SuppressLint("LogNotTimber")
internal class FileTree(path: String, fileCount: Int = 3): Timber.Tree(), Flushable, Closeable {

    // Custom logger class that has no references to LogManager. Otherwise, file logs are printed to Logcat.
    private val logger: Logger = object : Logger("FileLogger", null) {}
    private val fileHandler: FileHandler = FileHandler(
        "$path/%g.txt",
        0,
        fileCount
    ).apply {
        formatter = NoFormatter
    }

    init {
        logger.run {
            level = priorityToLevel(Log.VERBOSE)
            addHandler(fileHandler)
        }
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val date = formatter.format(System.currentTimeMillis())
        val priorityLetter = priorityToLetter(priority)
        val formattedMessage = "$date $priorityLetter/$tag: $message\n"

        logger.log(priorityToLevel(priority), formattedMessage)
    }

    override fun flush() {
        Log.i("FileTree", "flush()")
        fileHandler.flush()
    }

    override fun close() {
        Log.i("FileTree", "close()")
        logger.removeHandler(fileHandler)
        fileHandler.close()
    }
}

private object NoFormatter : Formatter() {
    override fun format(record: LogRecord): String {
        return record.message
    }
}