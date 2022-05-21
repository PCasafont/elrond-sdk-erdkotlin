package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.wallet.Address

class GetAddressBalanceUsecase internal constructor(private val accountRepository: AccountRepository) {

    fun execute(address: Address) = accountRepository.getAddressBalance(address)

}
