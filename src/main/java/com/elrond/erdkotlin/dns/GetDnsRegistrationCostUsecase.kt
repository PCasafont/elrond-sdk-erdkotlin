package com.elrond.erdkotlin.dns

import com.elrond.erdkotlin.vm.query.QueryContractUsecase
import com.elrond.erdkotlin.wallet.Address
import java.math.BigInteger

class GetDnsRegistrationCostUsecase internal constructor(
    private val queryContractUsecase: QueryContractUsecase,
    private val computeDnsAddressUsecase: ComputeDnsAddressUsecase
) {
    suspend fun execute(shardId: Byte): BigInteger {
        return execute(computeDnsAddressUsecase.execute(shardId))
    }

    suspend fun execute(dnsAddress: Address): BigInteger {
        val result = queryContractUsecase.execute(dnsAddress, "getRegistrationCost")
        return when {
            result.returnData.isNullOrEmpty() -> BigInteger.ZERO
            else -> result.returnData[0].asBigInt
        }
    }
}
