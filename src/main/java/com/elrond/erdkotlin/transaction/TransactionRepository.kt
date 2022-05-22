package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.transaction.models.TransactionOnNetwork
import com.elrond.erdkotlin.transaction.models.TransactionHash
import com.elrond.erdkotlin.transaction.models.TransactionInfo
import com.elrond.erdkotlin.wallet.Address

internal class TransactionRepository(
    private val elrondProxy: ElrondProxy
) {
    suspend fun sendTransaction(transaction: Transaction): TransactionHash {
        val response = elrondProxy.sendTransaction(transaction)
        return TransactionHash(requireNotNull(response.data).txHash)
    }

    suspend fun getTransactions(address: Address): List<TransactionOnNetwork> {
        val response = elrondProxy.getAddressTransactions(address)
        return response.data?.transactions?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun estimateCostOfTransaction(transaction: Transaction): String {
        val response = elrondProxy.estimateCostOfTransaction(transaction)
        return requireNotNull(response.data).txGasUnits
    }

    suspend fun getTransactionInfo(txHash: String, sender: Address?): TransactionInfo {
        val response = elrondProxy.getTransactionInfo(txHash, sender)
        return requireNotNull(response.data).transaction.toDomain()
    }

    suspend fun getTransactionStatus(txHash: String, sender: Address?): String {
        val response = elrondProxy.getTransactionStatus(txHash, sender)
        return requireNotNull(response.data).status
    }
}
