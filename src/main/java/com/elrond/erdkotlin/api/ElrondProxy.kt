package com.elrond.erdkotlin.api

import com.elrond.erdkotlin.account.responses.GetAccountResponse
import com.elrond.erdkotlin.account.responses.GetAddressBalanceResponse
import com.elrond.erdkotlin.account.responses.GetAddressNonceResponse
import com.elrond.erdkotlin.account.responses.GetAddressTransactionsResponse
import com.elrond.erdkotlin.networkconfig.GetNetworkConfigResponse
import com.elrond.erdkotlin.transaction.responses.EstimateCostOfTransactionResponse
import com.elrond.erdkotlin.transaction.responses.GetTransactionInfoResponse
import com.elrond.erdkotlin.transaction.responses.GetTransactionStatusResponse
import com.elrond.erdkotlin.transaction.responses.SendTransactionResponse
import com.elrond.erdkotlin.vm.responses.QueryContractDigitResponse
import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.vm.responses.QueryContractResponse
import com.elrond.erdkotlin.vm.responses.QueryContractStringResponse
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.transaction.models.toDto
import com.elrond.erdkotlin.wallet.Address
import com.google.gson.Gson

internal class ElrondProxy(
    url: String
) {
    private val elrondClient = ElrondClient(url)

    fun setUrl(url: String) {
        elrondClient.url = url
    }

    suspend fun getNetworkConfig(): GetNetworkConfigResponse {
        return elrondClient.doGet("network/config")
    }

    // Addresses

    suspend fun getAccount(address: Address): GetAccountResponse {
        return elrondClient.doGet("address/${address.bech32()}")
    }

    suspend fun getAddressNonce(address: Address): GetAddressNonceResponse {
        return elrondClient.doGet("address/${address.bech32()}/nonce")
    }

    suspend fun getAddressBalance(address: Address): GetAddressBalanceResponse {
        return elrondClient.doGet("address/${address.bech32()}/balance")
    }

    suspend fun getAddressTransactions(address: Address): GetAddressTransactionsResponse {
        return elrondClient.doGet("address/${address.bech32()}/transactions")
    }

    // Transactions

    suspend fun sendTransaction(transaction: Transaction): SendTransactionResponse {
        return elrondClient.doPost("transaction/send", transaction.toDto())
    }

    suspend fun estimateCostOfTransaction(transaction: Transaction): EstimateCostOfTransactionResponse {
        return elrondClient.doPost("transaction/cost", transaction.toDto())
    }

    suspend fun getTransactionInfo(txHash: String, sender: Address?): GetTransactionInfoResponse {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32()}"
        }
        return elrondClient.doGet("transaction/$txHash$senderAddress")
    }

    suspend fun getTransactionStatus(txHash: String, sender: Address?): GetTransactionStatusResponse {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32()}"
        }
        return elrondClient.doGet("transaction/$txHash/status$senderAddress")
    }

    // VM

    // Compute Output of Pure Function
    suspend fun queryContract(queryContractInput: QueryContractInput): QueryContractResponse {
        return elrondClient.doPost("vm-values/query", queryContractInput)
    }

    suspend fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringResponse {
        return elrondClient.doPost("vm-values/hex", queryContractInput)
    }

    suspend fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringResponse {
        return elrondClient.doPost("vm-values/string", queryContractInput)
    }

    suspend fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitResponse {
        return elrondClient.doPost("vm-values/int", queryContractInput)
    }
}
