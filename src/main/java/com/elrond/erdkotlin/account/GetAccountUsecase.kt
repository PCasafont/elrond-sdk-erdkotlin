package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.wallet.Address

class GetAccountUsecase internal constructor(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(address: Address) = accountRepository.getAccount(address)
}
