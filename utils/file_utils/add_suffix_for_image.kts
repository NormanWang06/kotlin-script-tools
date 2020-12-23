#!/usr/bin/env kotlin

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

fun getImageSuffix(file: File): String? {
    val inputStream = FileInputStream(file)
    val jpg = listOf("FF", "D8")
    val bmp = listOf("42", "4D")
    val gif = listOf("47", "49", "46")
    val png = listOf("89", "50", "4E", "47", "0D", "0A", "1A", "0A")
    val imgTypes = hashMapOf("jpg" to jpg, "bmp" to bmp, "gif" to gif, "png" to png)

    val bytesIterated = ArrayList<String>()

    val byte = ByteArray(1) { 0 }
    for (i in 0 until 8) {
        val num = inputStream.read(byte)
        if (num == -1) {
            return null
        }

        bytesIterated.add(byte.joinToString("") { "%02x".format(it) }.toUpperCase(Locale.ROOT))
        val typeEntry = imgTypes.entries.find { bytesIterated.containsAll(it.value) }
        if (typeEntry != null) {
            return typeEntry.key
        }
    }

    return null
}