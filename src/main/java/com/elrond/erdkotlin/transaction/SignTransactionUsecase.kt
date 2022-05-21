package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.Exceptions
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.wallet.Wallet

internal class SignTransactionUsecase {

    @Throws(Exceptions.CannotSignTransactionException::class)
    fun execute(transaction: Transaction, wallet: Wallet) = try {
        transaction.copy(signature = wallet.sign(transaction.serialize()))
    } catch (error: Exceptions.CannotSerializeTransactionException) {
        throw Exceptions.CannotSignTransactionException()
    }
}
