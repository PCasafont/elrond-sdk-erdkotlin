package com.elrond.erdkotlin.helper

import com.elrond.erdkotlin.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.transaction.SignTransactionUsecase
import com.elrond.erdkotlin.transaction.TransactionRepository
import com.elrond.erdkotlin.transaction.models.TransactionHash
import io.mockk.every
import io.mockk.mockk

object TestUsecaseProvider {

    internal val transactionRepository: TransactionRepository = mockk {
        every { sendTransaction(any()) } returns TransactionHash("")
    }

    internal val sendTransactionUsecase = SendTransactionUsecase(
        SignTransactionUsecase(),
        transactionRepository
    )
}
