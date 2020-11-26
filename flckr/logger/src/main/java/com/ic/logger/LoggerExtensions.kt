package com.ic.logger

import android.content.Context
import timber.log.Timber
import java.io.Closeable
import java.io.Flushable

suspend fun Logger.Companion.sendLogs(context: Context, title: String, subject: String, to: String, fileName: String): Boolean {
    return config.fileLoggerConfig.fileLogsDirectory?.let {
        flush()
        LogSender(context, it).send(title, subject, to, fileName)
    } ?: false
}

fun Logger.Companion.flush() {
    Timber.forest()
        .filterIsInstance<Flushable>()
        .forEach(Flushable::flush)
}

fun Logger.Companion.close() {
    Timber.forest()
        .filterIsInstance<Closeable>()
        .forEach(Closeable::close)
}