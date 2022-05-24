package com.elrond.erdkotlin.account.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
internal data class GetAddressTransactionsResponse(
    val transactions: List<TransactionOnNetworkData>
) {
    @Serializable
    data class TransactionOnNetworkData(
        val sender: String,
        val receiver: String,
        val senderUsername: String? = null,
        val receiverUsername: String? = null,
        val nonce: Long,
        @Serializable(with = BigIntegerSerializer::class)
        val value: BigInteger,
        val gasPrice: Long,
        val gasLimit: Long,
        val signature: String,
        val hash: String,
        val data: String?,
        val status: String,
        val timestamp: Long,
        val gasUsed: Long,
        val receiverShard: Long,
        val senderShard: Long,
        val miniBlockHash: String,
        val round: Long,
        val searchOrder: Long,
        val fee: String,
        val scResults: List<ScResult>? = null,
        val hyperblockNonce: Long? = null
    ) {
        // source : https://github.com/ElrondNetwork/elrond-go/blob/2be09d2377993cda87cef7b4167c915d8ea5f163/data/transaction/apiTransactionResult.go#L57
        @Serializable
        data class ScResult(
            val hash: String? = null,
            val nonce: Long,
            val gasLimit: Long,
            val gasPrice: Long,
            @Serializable(with = BigIntegerSerializer::class)
            val value: BigInteger,
            val sender: String,
            val receiver: String,
            val relayedValue: String? = null,
            val data: String? = null,
            val prevTxHash: String,
            val originalTxHash: String,
            val callType: String,
            val relayerAddress: String? = null,
            val code: String? = null,
            val codeMetadata: String? = null,
            val returnMessage: String? = null,
            val originalSender: String? = null,
        )
    }
}
