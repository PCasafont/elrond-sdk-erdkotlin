package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.CannotSerializeTransactionException
import com.elrond.erdkotlin.CannotSignTransactionException
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.wallet.Wallet

internal class SignTransactionUsecase {

    fun execute(transaction: Transaction, wallet: Wallet) = try {
        transaction.copy(signature = wallet.sign(transaction.serialize()))
    } catch (error: CannotSerializeTransactionException) {
        throw CannotSignTransactionException()
    }
}
