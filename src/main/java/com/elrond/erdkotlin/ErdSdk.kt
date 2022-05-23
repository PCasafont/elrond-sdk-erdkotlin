package com.elrond.erdkotlin

import com.elrond.erdkotlin.account.*
import com.elrond.erdkotlin.account.AccountRepository
import com.elrond.erdkotlin.api.gateway.ElrondGateway
import com.elrond.erdkotlin.networkconfig.NetworkConfigRepository
import com.elrond.erdkotlin.vm.VmRepository
import com.elrond.erdkotlin.transaction.*
import com.elrond.erdkotlin.transaction.TransactionRepository
import com.elrond.erdkotlin.dns.computeDnsAddress
import com.elrond.erdkotlin.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.transaction.models.Transaction
import com.elrond.erdkotlin.utils.scArg
import com.elrond.erdkotlin.utils.toHex
import com.elrond.erdkotlin.vm.query.QueryContractInput
import com.elrond.erdkotlin.vm.query.QueryContractOutput
import com.elrond.erdkotlin.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.vm.query.string.QueryContractStringOutput
import com.elrond.erdkotlin.wallet.*
import org.bouncycastle.util.encoders.Base64
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger
import java.nio.charset.StandardCharsets

class ErdSdk(
    elrondNetwork: ElrondNetwork = ElrondNetwork.DevNet
) {
    private val elrondGateway = ElrondGateway(elrondNetwork.url)
    private val networkConfigRepository = NetworkConfigRepository(elrondGateway)
    private val accountRepository = AccountRepository(elrondGateway)
    private val transactionRepository = TransactionRepository(elrondGateway)
    private val vmRepository = VmRepository(elrondGateway)

    suspend fun getAccount(address: Address) =
        accountRepository.getAccount(address)

    suspend fun getAddressNonce(address: Address) =
        accountRepository.getAddressNonce(address)

    suspend fun getAddressBalance(address: Address) =
        accountRepository.getAddressBalance(address)

    suspend fun getNetworkConfig() =
        networkConfigRepository.getNetworkConfig()

    suspend fun sendTransaction(transaction: Transaction, wallet: Wallet): Transaction {
        val signedTransaction = when {
            transaction.isSigned -> transaction
            else -> wallet.sign(transaction)
        }
        return transactionRepository.sendTransaction(signedTransaction).let { sentTransaction ->
            signedTransaction.copy(txHash = sentTransaction.hash)
        }
    }

    suspend fun getTransactions(address: Address) =
        transactionRepository.getTransactions(address)

    suspend fun getTransactionInfo(txHash: String, sender: Address? = null) =
        transactionRepository.getTransactionInfo(txHash, sender)

    suspend fun getTransactionStatus(txHash: String, sender: Address? = null) =
        transactionRepository.getTransactionStatus(txHash, sender)

    suspend fun estimateCostOfTransaction(transaction: Transaction) =
        transactionRepository.estimateCostOfTransaction(transaction)

    suspend fun sendEsdtTransaction(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        tokenId: String,
        receiver: Address,
        amount: BigInteger,
        gasPrice: Long = 1000000000,
        gasLimit: Long = 500_000,
    ): Transaction {
        val transaction = Transaction(
            nonce = account.nonce,
            receiver = receiver,
            sender = account.address,
            chainID = networkConfig.chainID,
            gasPrice = gasPrice,
            gasLimit = gasLimit,
            data = "ESDTTransfer${tokenId.scArg()}${amount.scArg()}"
        )
        return sendTransaction(transaction, wallet)
    }

    suspend fun queryContract(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContract(payload)
    }

    suspend fun queryContractHex(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractStringOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContractHex(payload)
    }

    suspend fun queryContractString(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractStringOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContractString(payload)
    }

    suspend fun queryContractInt(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractDigitOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContractInt(payload)
    }

    suspend fun callContract(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        gasLimit: Long,
        contractAddress: Address,
        funcName: String,
        args: List<Any> = emptyList(),
        value: BigInteger = BigInteger.ZERO,
    ): Transaction {
        val transaction = Transaction(
            nonce = account.nonce,
            receiver = contractAddress,
            sender = account.address,
            chainID = networkConfig.chainID,
            gasPrice = gasPrice,
            gasLimit = gasLimit,
            value = value,
            data = args.fold(funcName) { acc, arg -> acc + arg.scArg() }
        )
        return sendTransaction(transaction, wallet)
    }

    suspend fun getDnsRegistrationCost(shardId: Byte): BigInteger {
        return getDnsRegistrationCost(computeDnsAddress(shardId))
    }

    suspend fun getDnsRegistrationCost(dnsAddress: Address): BigInteger {
        val result = queryContract(dnsAddress, "getRegistrationCost")
        return when {
            result.returnData.isNullOrEmpty() -> BigInteger.ZERO
            else -> result.returnData[0].asBigInt
        }
    }

    suspend fun registerDns(
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
            value = getDnsRegistrationCost(dnsAddress),
            data = "register@${String(encodedName)}"
        )
        return sendTransaction(transaction, wallet)
    }
}

sealed class ElrondNetwork(
    val url: String
) {
    object MainNet : ElrondNetwork("https://gateway.elrond.com")
    object DevNet : ElrondNetwork("https://devnet-gateway.elrond.com")
    object TestNet : ElrondNetwork("https://testnet-gateway.elrond.com")
    class Custom(url: String) : ElrondNetwork(url)
}
