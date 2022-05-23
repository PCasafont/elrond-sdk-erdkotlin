package com.elrond.erdkotlin.utils

import java.math.BigInteger

private const val hexPrefix = "0x"

fun Any.scArg(): String = when (this) {
    is String ->
        if (startsWith(hexPrefix, ignoreCase = true)) {
            toString().numericScArg()
        } else {
            "@${encodeToByteArray().toHex()}"
        }
    is Int, is Long, is BigInteger ->
        toString().numericScArg()
    else ->
        error("Unsupported type: ${this::class.simpleName}")
}

private fun String.numericScArg(): String {
    if (startsWith(hexPrefix)){
        return "@${substring(startIndex = hexPrefix.length)}"
    }

    if (!isDigitsOnly()){
        throw IllegalArgumentException("unknown format for $this")
    }

    val hex = toBigInteger().toString(16)
    if (hex.length % 2 == 1){
        return "@0$hex"
    }
    return "@$hex"
}

private fun String.isDigitsOnly(): Boolean {
    val integerChars = '0'..'9'
    return all { it in integerChars }
}