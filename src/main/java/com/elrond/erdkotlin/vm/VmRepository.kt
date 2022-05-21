package com.elrond.erdkotlin.vm

import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.vm.query.QueryContractOutput
import com.elrond.erdkotlin.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.vm.query.string.QueryContractStringOutput

internal interface VmRepository {

    fun queryContract(queryContractInput: QueryContractInput): QueryContractOutput
    fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringOutput
    fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringOutput
    fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitOutput

}
