package com.elrond.erdkotlin.transaction

import com.elrond.erdkotlin.CannotSerializeTransactionException
import com.elrond.erdkotlin.CannotSignTransactionException
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.wallet.Wallet

fun Wallet.sign(transaction: Transaction) = try {
    transaction.copy(signature = sign(transaction.serialize()))
} catch (error: CannotSerializeTransactionException) {
    throw CannotSignTransactionException()
}
