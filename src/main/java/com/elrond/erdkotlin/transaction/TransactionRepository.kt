package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.Exceptions
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.transaction.models.TransactionOnNetwork
import com.elrond.erdkotlin.transaction.models.TransactionHash
import com.elrond.erdkotlin.transaction.models.TransactionInfo
import com.elrond.erdkotlin.wallet.Address
import java.io.IOException

internal interface TransactionRepository {

    @Throws(
        IOException::class,
        Exceptions.CannotSerializeTransactionException::class,
        Exceptions.ProxyRequestException::class
    )
    fun sendTransaction(transaction: Transaction): TransactionHash

    @Throws(
        IOException::class,
        Exceptions.CannotSerializeTransactionException::class,
        Exceptions.ProxyRequestException::class
    )
    fun getTransactions(address: Address): List<TransactionOnNetwork>

    @Throws(
        IOException::class,
        Exceptions.CannotSerializeTransactionException::class,
        Exceptions.ProxyRequestException::class
    )
    fun estimateCostOfTransaction(transaction: Transaction): String

    @Throws(
        IOException::class,
        Exceptions.CannotSerializeTransactionException::class,
        Exceptions.ProxyRequestException::class
    )
    fun getTransactionInfo(txHash: String, sender: Address?): TransactionInfo

    @Throws(
        IOException::class,
        Exceptions.CannotSerializeTransactionException::class,
        Exceptions.ProxyRequestException::class
    )
    fun getTransactionStatus(txHash: String, sender: Address?): String
}
