package com.elrond.erdkotlin.account.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
internal data class GetAddressBalanceResponse(
    @Serializable(with = BigIntegerSerializer::class)
    val balance: BigInteger
)
