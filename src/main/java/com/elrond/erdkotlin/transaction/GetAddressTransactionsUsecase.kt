package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.wallet.Address

class GetAddressTransactionsUsecase internal constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(address: Address) = transactionRepository.getTransactions(address)
}
