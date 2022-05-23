package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.api.gateway.ElrondGateway
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.wallet.Address
import java.math.BigInteger

internal class AccountRepository(
    private val elrondGateway: ElrondGateway
) {
    suspend fun getAccount(address: Address): Account {
        val response = elrondGateway.getAccount(address)
        val payload = response.account
        return payload.toDomain(address)
    }

    suspend fun getAddressNonce(address: Address): Long {
        val response = elrondGateway.getAddressNonce(address)
        return response.nonce
    }

    suspend fun getAddressBalance(address: Address): BigInteger {
        val response = elrondGateway.getAddressBalance(address)
        return response.balance
    }

    suspend fun getAddressEsdtBalance(address: Address, tokenId: String): BigInteger {
        val response = elrondGateway.getAddressEsdtBalance(address, tokenId)
        return response.tokenData.balance
    }
}
