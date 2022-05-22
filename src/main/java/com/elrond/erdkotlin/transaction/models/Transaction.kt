package com.elrond.erdkotlin.transaction.models

import com.elrond.erdkotlin.wallet.Address
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import org.bouncycastle.util.encoders.Base64

private val json = Json {
    encodeDefaults = false
}
const val TRANSACTION_VERSION_DEFAULT = 1
const val TRANSACTION_VERSION_TX_HASH_SIGN = 2
const val TRANSACTION_OPTION_NONE = 0
const val TRANSACTION_OPTION_TX_HASH_SIGN = 1

data class Transaction(
    val sender: Address,
    val receiver: Address,
    val chainID: String,
    val senderUsername: String? = null,
    val receiverUsername: String? = null,
    val nonce: Long = 0,
    val value: BigInteger = BigInteger.ZERO,
    val gasPrice: Long = 1000000000,
    val gasLimit: Long = 50000,
    val version: Int = TRANSACTION_VERSION_DEFAULT,
    val data: String? = null,
    val option: Int = TRANSACTION_OPTION_NONE,
    val signature: String = "",
    val txHash: String = ""
) {
    val isSigned = signature.isNotEmpty()
    val isSent = txHash.isNotEmpty()
}

@Serializable
class TransactionDto(
    val nonce: Long,
    val value: String,
    val receiver: String,
    val sender: String,
    val senderUsername: String? = null,
    val receiverUsername: String? = null,
    val gasPrice: Long,
    val gasLimit: Long,
    val data: String? = null,
    val chainID: String,
    val version: Int,
    val option: Int? = null,
    val signature: String? = null
)

fun Transaction.toDto() = TransactionDto(
    nonce = nonce,
    value = value.toString(10),
    receiver = receiver.bech32(),
    sender = sender.bech32(),
    senderUsername = senderUsername.takeIf { !it.isNullOrEmpty() }?.encode(),
    receiverUsername = receiverUsername.takeIf { !it.isNullOrEmpty() }?.encode(),
    gasPrice = gasPrice,
    gasLimit = gasLimit,
    data = data.takeIf { !it.isNullOrEmpty() }?.encode(),
    chainID = chainID,
    version = version,
    option.takeIf { it != TRANSACTION_OPTION_NONE },
    signature.takeIf { it.isNotEmpty() }
)

fun Transaction.serialize() = json.encodeToString(toDto())

private fun String.encode(): String {
    val dataAsBytes: ByteArray = toByteArray(StandardCharsets.UTF_8)
    val encodedAsBytes: ByteArray = Base64.encode(dataAsBytes)
    return String(encodedAsBytes)
}
