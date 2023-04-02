#!/usr/bin/env kotlin
@file:Import("../image_common.kts")

import java.io.File
import java.io.FileInputStream

val from = "C:\\Users\\Norman\\Desktop\\QQfile_recv_flat"
val to = "C:\\Users\\Norman\\Desktop\\QQfile_recv_flat-images"

pickUpImages(File(from), File(to))

fun pickUpImages(source: File, dest: File) {
    if (source.isDirectory) {
        source.listFiles()?.forEach {
            if (it.isFile && it.canRead() && isImage(it)) {
                it.copyTo(File(dest.absolutePath + File.separator + it.name))
            } else if (it.isDirectory && it.canRead()) {
                val destSubDir = File(dest.absolutePath + File.separator + it.name)
                destSubDir.mkdir()
                pickUpImages(it, destSubDir)
            }
        }
    } else if (source.isFile && isImage(source)) {
        if (dest.isDirectory) {
            source.copyTo(File(dest.absolutePath + File.separator + source.name))
        } else if (dest.isFile) {
            source.copyTo(dest)
        }
    }
}