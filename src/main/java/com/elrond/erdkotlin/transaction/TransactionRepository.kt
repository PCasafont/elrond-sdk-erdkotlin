package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.transaction.models.TransactionOnNetwork
import com.elrond.erdkotlin.transaction.models.TransactionHash
import com.elrond.erdkotlin.transaction.models.TransactionInfo
import com.elrond.erdkotlin.wallet.Address

internal interface TransactionRepository {

    fun sendTransaction(transaction: Transaction): TransactionHash

    fun getTransactions(address: Address): List<TransactionOnNetwork>

    fun estimateCostOfTransaction(transaction: Transaction): String

    fun getTransactionInfo(txHash: String, sender: Address?): TransactionInfo

    fun getTransactionStatus(txHash: String, sender: Address?): String
}
