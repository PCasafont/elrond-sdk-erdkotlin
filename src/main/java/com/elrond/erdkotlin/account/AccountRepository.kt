package com.elrond.erdkotlin.account

import com.elrond.erdkotlin.wallet.Address
import java.io.IOException
import java.math.BigInteger

internal interface AccountRepository {

    fun getAccount(address: Address): Account

    fun getAddressNonce(address: Address): Long

    fun getAddressBalance(address: Address): BigInteger
}
