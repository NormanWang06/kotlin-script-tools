#!/usr/bin/env kotlin
@file:Import("../image_common.kts")

import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.system.exitProcess

val tempFile = HashSet<String>()
val from = "C:\\Users\\Norman\\Desktop\\chatpic_flat"
//addSuffixForImage(File(from))
pickDuplicatedFiles(File(from))
deleteFiles()

fun addSuffixForImage(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { addSuffixForImage(it) }
    } else if (file.isFile && !file.name.contains(".")) {
        val suffix = getImageSuffix(file)
        if (suffix != null) {
            val renamed = file.absolutePath + "." + suffix
            file.copyTo(File(renamed))
            val success = file.renameTo(File(renamed))
            println("$renamed success: ")
        }
    }
}

fun pickDuplicatedFiles(file: File) {
    if (file.isDirectory) {
        file.listFiles()?.forEach { pickDuplicatedFiles(it) }
    } else if (file.isFile && !file.name.contains(".")) {
        val suffix = getImageSuffix(file) ?: return
        val renamed = File(file.absolutePath + "." + suffix)
        if (renamed.exists()) {
            tempFile.add(file.absolutePath)
        }
    }
}

fun deleteFiles() {
    tempFile.forEach {
        File(it).delete()
    }
}