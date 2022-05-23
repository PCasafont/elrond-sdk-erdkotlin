package com.elrond.erdkotlin.account.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.math.BigInteger

@Serializable
internal data class GetAddressEsdtBalanceResponse(
    @Serializable
    val tokenData: TokenData
) {
    @Serializable
    internal data class TokenData(
        @Serializable(with = BigIntegerSerializer::class)
        val balance: BigInteger,
        val properties: String,
        val tokenIdentifier: String
    )
}
