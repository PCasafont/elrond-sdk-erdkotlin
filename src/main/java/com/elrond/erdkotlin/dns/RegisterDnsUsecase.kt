package com.elrond.erdkotlin.dns

import com.elrond.erdkotlin.account.Account
import com.elrond.erdkotlin.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.wallet.Wallet
import org.bouncycastle.util.encoders.Hex
import java.nio.charset.StandardCharsets

class RegisterDnsUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase,
    private val getRegistrationCostUsecase: GetDnsRegistrationCostUsecase
) {
    suspend fun execute(
        username: String,
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        gasLimit: Long
    ): Transaction {
        val dnsAddress = computeDnsAddress(username)
        val encodedName = Hex.encode(username.toByteArray(StandardCharsets.UTF_8))
        val transaction = Transaction(
            nonce = account.nonce,
            receiver = dnsAddress,
            sender = account.address,
            chainID = networkConfig.chainID,
            gasPrice = gasPrice,
            gasLimit = gasLimit,
            value = getRegistrationCostUsecase.execute(dnsAddress),
            data = "register@${String(encodedName)}"
        )
        return sendTransactionUsecase.execute(transaction, wallet)
    }

}
