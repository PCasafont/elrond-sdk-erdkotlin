package com.elrond.erdkotlin.vm.query

data class QueryContractInput(
    val scAddress: String,
    val funcName: String,
    val args: List<String>,
    val caller: String? = null,
    val value: String? = null
)
