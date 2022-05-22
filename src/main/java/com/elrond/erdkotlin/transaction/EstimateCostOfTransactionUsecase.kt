package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.transaction.models.Transaction

class EstimateCostOfTransactionUsecase internal constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(transaction: Transaction) =
        transactionRepository.estimateCostOfTransaction(transaction)
}
