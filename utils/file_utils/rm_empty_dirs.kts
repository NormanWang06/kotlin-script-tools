#!/usr/bin/env kotlin
import java.io.File

removeEmptyDir(File("/Users/neulion/Desktop/qq"))

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