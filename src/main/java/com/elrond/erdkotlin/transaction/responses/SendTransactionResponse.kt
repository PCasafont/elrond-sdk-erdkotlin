package com.elrond.erdkotlin.transaction.responses

import kotlinx.serialization.Serializable

@Serializable
internal class SendTransactionResponse(
    val txHash: String
)
