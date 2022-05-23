package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.api.gateway.ElrondGateway
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.transaction.models.TransactionOnNetwork
import com.elrond.erdkotlin.transaction.models.TransactionHash
import com.elrond.erdkotlin.transaction.models.TransactionInfo
import com.elrond.erdkotlin.wallet.Address

internal class TransactionRepository(
    private val elrondGateway: ElrondGateway
) {
    suspend fun sendTransaction(transaction: Transaction): TransactionHash {
        val response = elrondGateway.sendTransaction(transaction)
        return TransactionHash(response.txHash)
    }

    suspend fun getTransactions(address: Address): List<TransactionOnNetwork> {
        val response = elrondGateway.getAddressTransactions(address)
        return response.transactions.map { it.toDomain() }
    }

    suspend fun estimateCostOfTransaction(transaction: Transaction): Long {
        val response = elrondGateway.estimateCostOfTransaction(transaction)
        return response.txGasUnits
    }

    suspend fun getTransactionInfo(txHash: String, sender: Address?): TransactionInfo {
        val response = elrondGateway.getTransactionInfo(txHash, sender)
        return response.transaction.toDomain()
    }

    suspend fun getTransactionStatus(txHash: String, sender: Address?): String {
        val response = elrondGateway.getTransactionStatus(txHash, sender)
        return response.status
    }
}
