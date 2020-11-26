package com.ic.logger

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

internal class LogSender(private val context: Context, private val srcDir: File) {

    suspend fun send(title: String, subject: String, to: String, filename: String): Boolean {
        try {
            val dest = zipDirectory(filename) ?: return false
            val fileUri = FileProvider.getUriForFile(context, "com.ic.app.logger.fileprovider", dest)
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "application/zip"

                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_STREAM, fileUri)
                putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            }

            if (intent.resolveActivity(context.packageManager) != null) {
                val chooser = Intent.createChooser(intent, title)
                context.startActivity(chooser)
                return true
            }
        } catch (e: IllegalArgumentException) {
            L.error(e) { "send()" }
            return false
        }

        return false
    }

    private suspend fun zipDirectory(filename: String): File? = withContext(Dispatchers.IO) {
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
            L.warn { "zipDirectory(): storage is not writeable" }
            return@withContext null
        }

        if (!srcDir.isDirectory) {
            L.warn { "zipDirectory(): $srcDir is not a directory" }
            return@withContext null
        }

        val dest = File(context.externalCacheDir, "$filename.zip")
        try {
            zip(srcDir, dest) { it.extension == "txt" }
            dest
        } catch (e: IOException) {
            L.warn(e) { "zipDirectory(): cannot zip file" }
            dest.delete()
            null
        }
    }

    private fun zip(src: File, dest: File, filter: (file: File) -> Boolean = { true }) {
        dest.outputStream()
            .let(::ZipOutputStream)
            .use {
                zip(src, src.name, filter, it)
            }
    }

    private fun zip(current: File, entryName: String, filter: (file: File) -> Boolean = { true }, out: ZipOutputStream) {
        if (current.isDirectory) {
            out.putNextEntry(ZipEntry("$entryName/"))

            for (childFile in current.listFiles(filter) ?: emptyArray()) {
                zip(childFile, entryName + "/" + childFile.name, filter, out)
            }
        } else {
            out.putNextEntry(ZipEntry(entryName))
            current.inputStream().use { it.copyTo(out) }
        }
    }

    companion object {
        private val L = Logger()
    }
}