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
import com.elrond.erdkotlin.wallet.Address
import com.google.gson.Gson
import okhttp3.OkHttpClient

internal class ElrondProxy(
    url: String
) {
    private val gson = Gson()
    private val elrondClient = ElrondClient(url, gson)

    fun setUrl(url: String) {
        elrondClient.url = url
    }

    suspend fun getNetworkConfig(): ElrondClient.ResponseBase<GetNetworkConfigResponse> {
        return elrondClient.doGet("network/config")
    }

    // Addresses

    suspend fun getAccount(address: Address): ElrondClient.ResponseBase<GetAccountResponse> {
        return elrondClient.doGet("address/${address.bech32()}")
    }

    suspend fun getAddressNonce(address: Address): ElrondClient.ResponseBase<GetAddressNonceResponse> {
        return elrondClient.doGet("address/${address.bech32()}/nonce")
    }

    suspend fun getAddressBalance(address: Address): ElrondClient.ResponseBase<GetAddressBalanceResponse> {
        return elrondClient.doGet("address/${address.bech32()}/balance")
    }

    suspend fun getAddressTransactions(address: Address): ElrondClient.ResponseBase<GetAddressTransactionsResponse> {
        return elrondClient.doGet("address/${address.bech32()}/transactions")
    }

    // Transactions

    suspend fun sendTransaction(transaction: Transaction): ElrondClient.ResponseBase<SendTransactionResponse> {
        val requestJson = transaction.serialize()
        return elrondClient.doPost("transaction/send", requestJson)
    }

    suspend fun estimateCostOfTransaction(transaction: Transaction): ElrondClient.ResponseBase<EstimateCostOfTransactionResponse> {
        return elrondClient.doPost("transaction/cost", transaction.serialize())
    }

    suspend fun getTransactionInfo(txHash: String, sender: Address?): ElrondClient.ResponseBase<GetTransactionInfoResponse> {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32()}"
        }
        return elrondClient.doGet("transaction/$txHash$senderAddress")
    }

    suspend fun getTransactionStatus(txHash: String, sender: Address?): ElrondClient.ResponseBase<GetTransactionStatusResponse> {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32()}"
        }
        return elrondClient.doGet("transaction/$txHash/status$senderAddress")
    }

    // VM

    // Compute Output of Pure Function
    suspend fun queryContract(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractResponse> {
        return elrondClient.doPost("vm-values/query", gson.toJson(queryContractInput))
    }

    suspend fun queryContractHex(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractStringResponse> {
        return elrondClient.doPost("vm-values/hex", gson.toJson(queryContractInput))
    }

    suspend fun queryContractString(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractStringResponse> {
        return elrondClient.doPost("vm-values/string", gson.toJson(queryContractInput))
    }

    suspend fun queryContractInt(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractDigitResponse> {
        return elrondClient.doPost("vm-values/int", gson.toJson(queryContractInput))
    }

}
