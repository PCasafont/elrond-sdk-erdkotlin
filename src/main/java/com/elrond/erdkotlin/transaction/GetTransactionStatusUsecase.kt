package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.wallet.Address

class GetTransactionStatusUsecase internal constructor(private val transactionRepository: TransactionRepository) {

    suspend fun execute(txHash: String, sender: Address? = null) =
        transactionRepository.getTransactionStatus(txHash, sender)
}
