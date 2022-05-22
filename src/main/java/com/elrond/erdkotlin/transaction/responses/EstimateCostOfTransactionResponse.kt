package com.elrond.erdkotlin.transaction.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class EstimateCostOfTransactionResponse(
    val txGasUnits: String
)
