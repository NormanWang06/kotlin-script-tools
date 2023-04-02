#!/usr/bin/env kotlin

import java.io.File
import java.io.FileInputStream

pickUpImages(File("/Users/neulion/Desktop/flatqq"), File("/Users/neulion/Desktop/flatqq-images"))

fun pickUpImages(source: File, dest: File) {
    if (source.isDirectory) {
        source.listFiles()?.forEach {
            if (it.isFile && it.canRead() && isStaticImage(it)) {
                it.copyTo(File(dest.absolutePath + File.separator + it.name))
            } else if (it.isDirectory && it.canRead()) {
                val destSubDir = File(dest.absolutePath + File.separator + it.name)
                destSubDir.mkdir()
                pickUpImages(it, destSubDir)
            }
        }
    } else if (source.isFile && isStaticImage(source)) {
        if (dest.isDirectory) {
            source.copyTo(File(dest.absolutePath + File.separator + source.name))
        } else if (dest.isFile) {
            source.copyTo(dest)
        }
    }
}

fun isStaticImage(file: File): Boolean {
    if (hasStaticImageSuffix(file)) {
        return true
    }

    val inputStream = FileInputStream(file)
    val jpg = listOf("FF", "D8")
    val bmp = listOf("42", "4D")
    val png = listOf("89", "50", "4E", "47", "0D", "0A", "1A", "0A")
    val imgTypes = listOf(jpg, bmp, png)

    val bytesIterated = ArrayList<String>()

    val byte = ByteArray(1) { 0 }
    for (i in 0 until 8) {
        val num = inputStream.read(byte)
        if (num == -1) {
            return false
        }

        bytesIterated.add(byte.joinToString("") { "%02x".format(it) })
        val isImage = imgTypes.any { bytesIterated.containsAll(it) }
        if (isImage) {
            return true
        }
    }

    return false
}

fun hasStaticImageSuffix(file: File): Boolean {
    val fileName = file.name
    return fileName.endsWith(".jpg")
            || fileName.endsWith(".png")
            || fileName.endsWith(".bmp")
}