#!/usr/bin/env kotlin

import java.io.File

println("============= Starting Delete Build Directory =============")

val path = "/Users/neulion/Development/"
println("dir path: $path")

removeBuildDir(File(path))

println("============= Delete Build Directory Finished =============")

fun removeBuildDir(file: File) {
    if (file.isDirectory) {
        if (file.name == "build") {
            file.deleteRecursively()
            println("dir: \"" + file.absolutePath + "\" deleted.")
        } else {
            file.listFiles()?.forEach { removeBuildDir(it) }
        }
    }
}