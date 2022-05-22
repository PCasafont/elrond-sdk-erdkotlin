package com.elrond.erdkotlin.vm.query

import com.elrond.erdkotlin.vm.VmRepository
import com.elrond.erdkotlin.wallet.Address

class QueryContractUsecase internal constructor(
    private val vmRepository: VmRepository
) {

    suspend fun execute(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContract(payload)
    }
}
