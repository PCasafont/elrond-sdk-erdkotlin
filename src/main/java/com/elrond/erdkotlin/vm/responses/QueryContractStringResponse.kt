package com.elrond.erdkotlin.vm.responses

import kotlinx.serialization.Serializable

@Serializable
data class QueryContractStringResponse(
    val data: String
)
