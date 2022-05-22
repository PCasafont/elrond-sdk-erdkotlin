package com.elrond.erdkotlin.vm

import com.elrond.erdkotlin.api.gateway.ElrondGateway
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.vm.query.QueryContractOutput
import com.elrond.erdkotlin.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.vm.query.string.QueryContractStringOutput

internal class VmRepository(
    private val elrondGateway: ElrondGateway
) {
    suspend fun queryContract(queryContractInput: QueryContractInput): QueryContractOutput {
        return requireNotNull(elrondGateway.queryContract(queryContractInput).data).toDomain()
    }

    suspend fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringOutput {
        return requireNotNull(elrondGateway.queryContractHex(queryContractInput)).toDomain()
    }

    suspend fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringOutput {
        return requireNotNull(elrondGateway.queryContractString(queryContractInput)).toDomain()
    }

    suspend fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitOutput {
        return requireNotNull(elrondGateway.queryContractInt(queryContractInput)).toDomain()
    }

}
