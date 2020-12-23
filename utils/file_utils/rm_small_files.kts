#!/usr/bin/env kotlin
// args[0] is file path
// args[1] is smallest file size in bytes (Incluse)

import java.io.File
import kotlin.system.exitProcess

//removeSmallFiles("/Users/neulion/Desktop/flatqq")
println("========================================")

val fileName = readFileNameFromArgs()
if (fileName.isNullOrEmpty()) {
    println("please input dir or file name!")
    exitProcess(1)
}

val fileSizeIncluse = readFileSizeFromArgs()
if (fileSizeIncluse == null) {
    println("file size: ${args[1]} is not correct, please input a number!")
    exitProcess(2)
}

removeSmallFiles(File(fileName), fileSizeIncluse ?: 0)

fun removeSmallFiles(file: File, fileSizeIncluse: Long) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { removeSmallFiles(it, fileSizeIncluse) }
    } else if (file.isFile && file.canRead() && file.length() <= fileSizeIncluse) {
        println("delete file: " + file.name)
        file.delete()
    }
}

fun readFileNameFromArgs(): String? {
    if (args.isNullOrEmpty()) {
        return null
    } else {
        return args[0]
    }
}

fun readFileSizeFromArgs(): Long? {
    if (args.size == 1) {
        return 0L
    } else if (args.size >= 2) {
        return args[1].toLongOrNull()
    } else {
        return null
    }
}