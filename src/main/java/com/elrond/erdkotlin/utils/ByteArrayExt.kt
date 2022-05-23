package com.elrond.erdkotlin.utils

internal fun ByteArray.toHex() = joinToString(separator = ""){
    "%02X".format((it.toInt() and 0xFF))
}
