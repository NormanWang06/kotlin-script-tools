#!/usr/bin/env kotlin
import java.io.File

val nameSet = HashSet<String>()

val from = "C:\\Users\\Norman\\Desktop\\chatpic"
val to = "C:\\Users\\Norman\\Desktop\\chatpic_flat"

flatDirs(File(from), File(to))


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