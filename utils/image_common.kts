import java.io.File
import java.io.FileInputStream

fun isImage(file: File): Boolean {
    if (hasImageSuffix(file)) {
        return true
    }

    val inputStream = FileInputStream(file)
    val jpg = listOf("FF", "D8")
    val bmp = listOf("42", "4D")
    val gif = listOf("47", "49", "46")
    val png = listOf("89", "50", "4E", "47", "0D", "0A", "1A", "0A")
    val imgTypes = listOf(jpg, bmp, gif, png)

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

fun hasImageSuffix(file: File): Boolean {
    val fileName = file.name
    return fileName.endsWith(".jpg")
            || fileName.endsWith(".png")
            || fileName.endsWith(".bmp")
            || fileName.endsWith(".gif")
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

        bytesIterated.add(byte.joinToString("") { "%02x".format(it) })
        val typeEntry = imgTypes.entries.find { bytesIterated.containsAll(it.value) }
        if (typeEntry != null) {
            return typeEntry.key
        }
    }

    return null
}