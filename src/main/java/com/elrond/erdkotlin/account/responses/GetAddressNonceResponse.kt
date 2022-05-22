package com.elrond.erdkotlin.account.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable

@Serializable
internal data class GetAddressNonceResponse(
    @Serializable(with = BigIntegerSerializer::class)
    val nonce: Long
)
