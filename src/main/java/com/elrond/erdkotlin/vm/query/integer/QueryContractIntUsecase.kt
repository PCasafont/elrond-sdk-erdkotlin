package com.elrond.erdkotlin.vm.query.integer

import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.vm.VmRepository
import com.elrond.erdkotlin.wallet.Address

class QueryContractIntUsecase internal constructor(
    private val vmRepository: VmRepository
) {

    fun execute(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractDigitOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContractInt(payload)
    }
}
