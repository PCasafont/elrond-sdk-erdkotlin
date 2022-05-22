package com.elrond.erdkotlin.vm.query.string

import com.elrond.erdkotlin.vm.VmRepository
import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.wallet.Address

class QueryContractStringUsecase internal constructor(
    private val vmRepository: VmRepository
) {

    suspend fun execute(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractStringOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContractString(payload)
    }
}
