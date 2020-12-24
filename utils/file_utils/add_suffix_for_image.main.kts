#!/usr/bin/env kotlin
@file:Import("../common.kts")

import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

addSuffixForImage(File("/Users/neulion/Desktop/flatqq"))

fun addSuffixForImage(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { addSuffixForImage(it) }
    } else if (file.isFile && !file.name.contains(".")) {
        val suffix = getImageSuffix(file)
        if (suffix != null) {
            println(file.absolutePath + "." + suffix)
            file.renameTo(File(file.absolutePath + "." + suffix))
        }
    }
}