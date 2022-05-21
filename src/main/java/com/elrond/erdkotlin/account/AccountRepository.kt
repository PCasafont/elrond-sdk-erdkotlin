package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.wallet.Address
import java.math.BigInteger

internal class AccountRepository(
    private val elrondProxy: ElrondProxy
) {
    fun getAccount(address: Address): Account {
        val response = elrondProxy.getAccount(address)
        val payload = requireNotNull(response.data).account
        return payload.toDomain(address)
    }

    fun getAddressNonce(address: Address): Long {
        val response = elrondProxy.getAddressNonce(address)
        return requireNotNull(response.data).nonce
    }

    fun getAddressBalance(address: Address): BigInteger {
        val response = elrondProxy.getAddressBalance(address)
        return requireNotNull(response.data).balance
    }
}
