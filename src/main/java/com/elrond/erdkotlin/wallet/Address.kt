package com.elrond.erdkotlin.wallet

import com.elrond.erdkotlin.*
import org.bitcoinj.core.Bech32
import org.bouncycastle.util.encoders.DecoderException
import org.bouncycastle.util.encoders.Hex
import java.io.ByteArrayOutputStream

private const val HRP = "erd"
private const val PUBKEY_LENGTH = 32
private const val PUBKEY_STRING_LENGTH = PUBKEY_LENGTH * 2 // hex-encoded
private const val BECH32_LENGTH = 62
private const val ZERO_PUBKEY_STRING = "0000000000000000000000000000000000000000000000000000000000000000"

data class Address internal constructor(
    val hex: String
) {
    fun pubkey(): ByteArray {
        return Hex.decode(hex)
    }

    fun bech32(): String {
        return Bech32.encode(Bech32.Encoding.BECH32, HRP, pubkey().convertBits(8, 5, true))
    }
}

fun createZeroAddress() = Address(ZERO_PUBKEY_STRING)

fun String.asBech32Address(): Address {
    val bech32Data = try {
        Bech32.decode(this)
    } catch (e: Exception) {
        throw CannotCreateBech32AddressException(this)
    }
    if (bech32Data.hrp != HRP) {
        throw BadAddressHrpException()
    }
    val decodedBytes: ByteArray = bech32Data.data.convertBits(5, 8, false)
    val hex = String(Hex.encode(decodedBytes))
    return Address(hex)
}

fun String.asHexAddress(): Address {
    if (length != PUBKEY_STRING_LENGTH || !isValidHex()) {
        throw CannotCreateAddressException(this)
    }
    return Address(this)
}

private fun String.isValidHex(): Boolean {
    return try {
        Hex.decode(this)
        true
    } catch (error: DecoderException) {
        false
    }
}

fun String.isValidBech32(): Boolean {
    return try {
        asBech32Address()
        true
    } catch (error: AddressException) {
        false
    }
}

/**
 * General power-of-2 base conversion.
 */
private fun ByteArray.convertBits(
    fromBits: Int,
    toBits: Int,
    pad: Boolean
): ByteArray {
    /*-
       Reference Python implementation by Pieter Wuille:

       def convertbits(data, frombits, tobits, pad=True):
           acc = 0
           bits = 0
           ret = []
           maxv = (1 << tobits) - 1
           max_acc = (1 << (frombits + tobits - 1)) - 1
           for value in data:
               if value < 0 or (value >> frombits):
                   return None
               acc = ((acc << frombits) | value) & max_acc
               bits += frombits
               while bits >= tobits:
                   bits -= tobits
                   ret.append((acc >> bits) & maxv)
           if pad:
               if bits:
                   ret.append((acc << (tobits - bits)) & maxv)
           elif bits >= frombits or ((acc << (tobits - bits)) & maxv):
               return None
           return ret
    */

    var acc = 0
    var bits = 0
    val ret = ByteArrayOutputStream()
    val maxv = (1 shl toBits) - 1
    val maxAcc = (1 shl fromBits + toBits - 1) - 1
    for (value in this) {
        val valueAsInt: Int = (value.toInt() and 0xff)
        if (valueAsInt ushr fromBits != 0) {
            throw CannotConvertBitsException()
        }
        acc = ((acc shl fromBits) or valueAsInt) and maxAcc
        bits += fromBits
        while (bits >= toBits) {
            bits -= toBits
            ret.write((acc ushr bits) and maxv)
        }
    }
    if (pad) {
        if (bits > 0) {
            ret.write((acc shl (toBits - bits)) and maxv)
        }
    } else if (bits >= fromBits || ((acc shl (toBits - bits)) and maxv) != 0) {
        throw CannotConvertBitsException()
    }
    return ret.toByteArray()
}

