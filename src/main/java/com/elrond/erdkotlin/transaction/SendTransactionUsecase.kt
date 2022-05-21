package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.wallet.Wallet

class SendTransactionUsecase internal constructor(
    private val transactionRepository: TransactionRepository
) {
    fun execute(transaction: Transaction, wallet: Wallet): Transaction {
        val signedTransaction = when {
            transaction.isSigned -> transaction
            else -> wallet.sign(transaction)
        }
        return transactionRepository.sendTransaction(signedTransaction).let { sentTransaction ->
            signedTransaction.copy(txHash = sentTransaction.hash)
        }
    }

}
