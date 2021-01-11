package com.neulion.android.base

import java.util.regex.Pattern

val provincesArray = "京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼".toCharArray()
val citiesArray = "ABCDEFGHJKLMNPQRSTUVWXY".toCharArray()
val embassyArray = "123".toCharArray()
val militaryRegionsArray = "QBCEGHJKLNSVZ".toCharArray()

val fullSerialPattern = Pattern.compile("[0-9A-HJ-NP-Z]{4}[0-9A-HJ-NP-Z学挂领试超练警]")
val fourSerialPattern = Pattern.compile("[0-9A-HJ-NP-Z]{4}")
val multiLetterPattern = Pattern.compile(".*[A-HJ-NP-Z]{3}.*")

val electricSerialPattern1 = Pattern.compile("[1-9]{2}[0-9]{3}[DF]")
val electricSerialPattern2 = Pattern.compile("[1-9DF][1-9A-HJ-NP-Z][0-9]{3}[1-9]")

val embassyPattern = Pattern.compile("[1-3][0-9]{2}[0-9A-HJ-NP-Z]{3}使")

fun validate(str: String): Boolean {
    if (str.length != 7 && str.length != 8) {
        return false
    }

    val firstChar = str[0]
    return when {
        provincesArray.contains(firstChar) -> {
            if (str.length == 8) {
                validateElectric(str)
            } else {
                validateNormal(str)
            }
        }

        embassyArray.contains(firstChar) -> validateEmbassy(str)
        militaryRegionsArray.contains(firstChar) -> validateArmy(str)
        str.startsWith("WJ") -> validateArmedPolice(str)
        str.startsWith("民航") -> validateAviation(str)
        else -> false
    }
}

fun validateElectric(str: String): Boolean {
    val serial = str.substring(2)
    return electricSerialPattern1.matcher(serial).matches()
            || electricSerialPattern2.matcher(serial).matches()
}

fun validateNormal(str: String): Boolean {
    val city = str[1]

    return if (citiesArray.contains(city)
            // 'O' almost cancelled, but still using in some region
            || city == 'O') {
        validateFullSerial(str.substring(2))
    } else if (city == 'Z' && str[0] == '粤') {
        validateHKAndMacao(str)
    } else {
        false
    }
}

fun validateHKAndMacao(str: String): Boolean {
    return if (str[6] == '港' || str[6] == '澳') {
        validate4Serial(str.substring(2, 6))
    } else {
        false
    }
}


fun validateEmbassy(str: String): Boolean {
    return embassyPattern.matcher(str).matches()
}

fun validateArmy(str: String): Boolean {
    val city = str[1]
    return if (citiesArray.contains(city)
            // 'O' almost cancelled, but still using in some region
            || city == 'O') {
        validateFullSerial(str.substring(2))
    } else {
        false
    }
}

fun validateArmedPolice(str: String): Boolean {
    val city = str[2]
    return if (str.length == 7) {
        validateFullSerial(str.substring(2))
    } else if (citiesArray.contains(city)
            // 'O' almost cancelled, but still using in some region
            || city == 'O') {
        validateFullSerial(str.substring(3))
    } else {
        false
    }
}

fun validateAviation(str: String): Boolean {
    return validateFullSerial(str.substring(3))
}

fun validate4Serial(serial: String): Boolean {
    return fourSerialPattern.matcher(serial).matches()
            && !multiLetterPattern.matcher(serial).matches()
}

fun validateFullSerial(serial: String): Boolean {
    return fullSerialPattern.matcher(serial).matches()
            && !multiLetterPattern.matcher(serial).matches()
}