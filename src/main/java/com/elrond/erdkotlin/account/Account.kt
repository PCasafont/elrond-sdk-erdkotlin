package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.wallet.Address
import java.math.BigInteger

data class Account(
    val address: Address,
    val nonce: Long = 0,
    val balance: BigInteger = BigInteger.ZERO,
    val code: String? = null,
    val username: String? = null
)
