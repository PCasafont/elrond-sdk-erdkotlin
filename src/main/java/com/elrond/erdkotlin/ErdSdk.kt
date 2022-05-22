package com.elrond.erdkotlin

import com.elrond.erdkotlin.account.AccountRepository
import com.elrond.erdkotlin.api.ElrondProxy
import com.elrond.erdkotlin.networkconfig.NetworkConfigRepository
import com.elrond.erdkotlin.vm.VmRepository
import com.elrond.erdkotlin.account.GetAccountUsecase
import com.elrond.erdkotlin.account.GetAddressBalanceUsecase
import com.elrond.erdkotlin.account.GetAddressNonceUsecase
import com.elrond.erdkotlin.dns.RegisterDnsUsecase
import com.elrond.erdkotlin.networkconfig.GetNetworkConfigUsecase
import com.elrond.erdkotlin.transaction.*
import com.elrond.erdkotlin.transaction.TransactionRepository
import com.elrond.erdkotlin.dns.GetDnsRegistrationCostUsecase
import com.elrond.erdkotlin.sc.CallContractUsecase
import com.elrond.erdkotlin.vm.query.QueryContractUsecase
import com.elrond.erdkotlin.vm.query.hex.QueryContractHexUsecase
import com.elrond.erdkotlin.vm.query.integer.QueryContractIntUsecase
import com.elrond.erdkotlin.vm.query.string.QueryContractStringUsecase

class ErdSdk(
    elrondNetwork: ElrondNetwork = ElrondNetwork.DevNet
) {
    private val elrondProxy = ElrondProxy(elrondNetwork.url)
    private val networkConfigRepository = NetworkConfigRepository(elrondProxy)
    private val accountRepository = AccountRepository(elrondProxy)
    private val transactionRepository = TransactionRepository(elrondProxy)
    private val vmRepository = VmRepository(elrondProxy)

    fun setNetwork(elrondNetwork: ElrondNetwork) {
        elrondProxy.setUrl(elrondNetwork.url)
    }

    fun getAccountUsecase() = GetAccountUsecase(accountRepository)
    fun getAddressNonceUsecase() = GetAddressNonceUsecase(accountRepository)
    fun getAddressBalanceUsecase() = GetAddressBalanceUsecase(accountRepository)
    fun getNetworkConfigUsecase() = GetNetworkConfigUsecase(networkConfigRepository)
    fun sendTransactionUsecase() = SendTransactionUsecase(transactionRepository)

    fun getTransactionsUsecase() = GetAddressTransactionsUsecase(transactionRepository)
    fun getTransactionInfoUsecase() = GetTransactionInfoUsecase(transactionRepository)
    fun getTransactionStatusUsecase() = GetTransactionStatusUsecase(transactionRepository)
    fun estimateCostOfTransactionUsecase() = EstimateCostOfTransactionUsecase(transactionRepository)
    fun queryContractUsecase() = QueryContractUsecase(vmRepository)
    fun queryContractHexUsecase() = QueryContractHexUsecase(vmRepository)
    fun queryContractStringUsecase() = QueryContractStringUsecase(vmRepository)
    fun queryContracInttUsecase() = QueryContractIntUsecase(vmRepository)
    fun callContractUsecase() = CallContractUsecase(sendTransactionUsecase())
    fun getDnsRegistrationCostUsecase() = GetDnsRegistrationCostUsecase(queryContractUsecase())

    fun registerDnsUsecase() = RegisterDnsUsecase(
        sendTransactionUsecase(),
        getDnsRegistrationCostUsecase()
    )
}

sealed class ElrondNetwork(
    val url: String
) {
    object MainNet : ElrondNetwork("https://gateway.elrond.com")
    object DevNet : ElrondNetwork("https://devnet-gateway.elrond.com")
    object TestNet : ElrondNetwork("https://testnet-gateway.elrond.com")
    class Custom(url: String) : ElrondNetwork(url)
}
