package com.elrond.erdkotlin.account.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
internal data class GetAccountResponse(
    val account: AccountData
) {
    @Serializable
    internal data class AccountData(
        val nonce: Long,
        @Serializable(with = BigIntegerSerializer::class)
        val balance: BigInteger,
        val code: String?,
        val username: String?
    )
}
