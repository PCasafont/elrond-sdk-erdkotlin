package com.elrond.erdkotlin.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigInteger


class BigIntegerSerializer : KSerializer<BigInteger> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BigInteger) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): BigInteger {
        val stringValue = decoder.decodeString()
        try {
            return stringValue.toBigInteger()
        } catch (e: Exception) {
            throw SerializationException("Error during deserialization of a numeric field ($stringValue)!", e)
        }
    }
}