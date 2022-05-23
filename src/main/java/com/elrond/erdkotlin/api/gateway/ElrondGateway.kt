package com.elrond.erdkotlin.api.gateway

import com.elrond.erdkotlin.account.responses.*
import com.elrond.erdkotlin.account.responses.GetAccountResponse
import com.elrond.erdkotlin.account.responses.GetAddressBalanceResponse
import com.elrond.erdkotlin.account.responses.GetAddressEsdtBalanceResponse
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

internal class ElrondGateway(
    url: String
) {
    private val client = ElrondGatewayHttpClient(url)

    suspend fun getNetworkConfig(): GetNetworkConfigResponse {
        return client.get("network/config")
    }

    // Addresses

    suspend fun getAccount(address: Address): GetAccountResponse {
        return client.get("address/${address.bech32()}")
    }

    suspend fun getAddressNonce(address: Address): GetAddressNonceResponse {
        return client.get("address/${address.bech32()}/nonce")
    }

    suspend fun getAddressBalance(address: Address): GetAddressBalanceResponse {
        return client.get("address/${address.bech32()}/balance")
    }

    suspend fun getAddressEsdtBalance(address: Address, tokenId: String): GetAddressEsdtBalanceResponse {
        return client.get("address/${address.bech32()}/esdt/$tokenId")
    }

    suspend fun getAddressTransactions(address: Address): GetAddressTransactionsResponse {
        return client.get("address/${address.bech32()}/transactions")
    }

    // Transactions

    suspend fun sendTransaction(transaction: Transaction): SendTransactionResponse {
        return client.post("transaction/send", transaction.toDto())
    }

    suspend fun estimateCostOfTransaction(transaction: Transaction): EstimateCostOfTransactionResponse {
        return client.post("transaction/cost", transaction.toDto())
    }

    suspend fun getTransactionInfo(txHash: String, sender: Address?): GetTransactionInfoResponse {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32()}"
        }
        return client.get("transaction/$txHash$senderAddress")
    }

    suspend fun getTransactionStatus(txHash: String, sender: Address?): GetTransactionStatusResponse {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32()}"
        }
        return client.get("transaction/$txHash/status$senderAddress")
    }

    // VM

    // Compute Output of Pure Function
    suspend fun queryContract(queryContractInput: QueryContractInput): QueryContractResponse {
        return client.post("vm-values/query", queryContractInput)
    }

    suspend fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringResponse {
        return client.post("vm-values/hex", queryContractInput)
    }

    suspend fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringResponse {
        return client.post("vm-values/string", queryContractInput)
    }

    suspend fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitResponse {
        return client.post("vm-values/int", queryContractInput)
    }
}
