package com.elrond.erdkotlin.transaction.responses

internal data class GetTransactionStatusResponse(
    val status: String // ex: "pending", "success"
)
