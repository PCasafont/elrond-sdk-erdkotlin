package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.wallet.Address

class GetAddressNonceUsecase internal constructor(private val accountRepository: AccountRepository) {

    suspend fun execute(address: Address) = accountRepository.getAddressNonce(address)

}
