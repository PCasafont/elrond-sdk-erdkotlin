package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.wallet.Address

class GetTransactionInfoUsecase internal constructor(private val transactionRepository: TransactionRepository) {

    fun execute(txHash: String, sender: Address? = null) =
        transactionRepository.getTransactionInfo(txHash, sender)
}
