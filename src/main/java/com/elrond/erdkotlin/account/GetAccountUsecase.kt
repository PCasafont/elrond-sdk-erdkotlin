package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.wallet.Address

class GetAccountUsecase internal constructor(private val accountRepository: AccountRepository) {

    fun execute(address: Address) = accountRepository.getAccount(address)

}
