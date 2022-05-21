package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.wallet.Address

class GetAddressTransactionsUsecase internal constructor(private val transactionRepository: TransactionRepository) {

    fun execute(address: Address) = transactionRepository.getTransactions(address)
}
