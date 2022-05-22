package com.elrond.erdkotlin.vm.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class QueryContractDigitResponse(
    @Serializable(with = BigIntegerSerializer::class)
    val data: BigInteger
)
