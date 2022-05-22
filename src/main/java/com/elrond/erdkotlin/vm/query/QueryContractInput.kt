package com.elrond.erdkotlin.vm.query

import kotlinx.serialization.Serializable

@Serializable
data class QueryContractInput(
    val scAddress: String,
    val funcName: String,
    val args: List<String>,
    val caller: String? = null,
    val value: String? = null
)
