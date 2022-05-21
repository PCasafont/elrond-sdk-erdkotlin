package com.elrond.erdkotlin.vm

import com.elrond.erdkotlin.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.vm.query.QueryContractOutput
import com.elrond.erdkotlin.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.vm.query.string.QueryContractStringOutput

internal class VmRepositoryImpl(private val elrondProxy: ElrondProxy) : VmRepository {

    override fun queryContract(queryContractInput: QueryContractInput): QueryContractOutput {
        return requireNotNull(elrondProxy.queryContract(queryContractInput).data).data.toDomain()
    }

    override fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringOutput {
        return requireNotNull(elrondProxy.queryContractHex(queryContractInput).data).toDomain()
    }

    override fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringOutput {
        return requireNotNull(elrondProxy.queryContractString(queryContractInput).data).toDomain()
    }

    override fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitOutput {
        return requireNotNull(elrondProxy.queryContractInt(queryContractInput).data).toDomain()
    }

}
