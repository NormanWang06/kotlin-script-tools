#!/usr/bin/env kotlin

import java.io.File
import java.util.*

printNoSuffixFiles(File("/Users/neulion/Desktop/flatqq"))

fun printNoSuffixFiles(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { printNoSuffixFiles(it) }
    } else if (file.isFile && !file.name.contains(".")) {
        println(file.absolutePath)
    }
}