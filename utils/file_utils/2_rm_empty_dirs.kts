#!/usr/bin/env kotlin
import java.io.File

val from = "C:\\Users\\Norman\\Desktop\\QQfile_recv"

removeEmptyDir(File(from))

fun removeEmptyDir(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { removeEmptyDir(it) }
        val subFiles = file.listFiles() ?: return
        if (subFiles.isEmpty()) {
            file.delete()
        }
    } else if (file.isFile && file.name == ".nomedia") {
        file.delete()
    }
}