#!/usr/bin/env kotlin
import java.io.File

flatDirs(File("/Users/neulion/Desktop/qq"), File("/Users/neulion/Desktop/flatqq"))

val nameSet = HashSet<String>()

fun flatDirs(source: File, desDir: File) {
    if (!desDir.exists()) {
        desDir.mkdirs()
    }

    if (source.isDirectory) {
        source.listFiles()?.forEach {
            flatDirs(it, desDir)
        }
    } else {
        var name = source.name
        while (nameSet.contains(name)) {
            name += "1"
        }
        source.copyTo(File(desDir.absolutePath + File.separator + name))
        nameSet.add(name)
    }
}