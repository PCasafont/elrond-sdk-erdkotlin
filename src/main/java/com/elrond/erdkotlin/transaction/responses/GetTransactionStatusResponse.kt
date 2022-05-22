package com.elrond.erdkotlin.transaction.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class GetTransactionStatusResponse(
    val status: String // ex: "pending", "success"
)
