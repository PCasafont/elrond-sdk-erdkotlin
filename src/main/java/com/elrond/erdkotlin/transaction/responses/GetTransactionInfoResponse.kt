package com.elrond.erdkotlin.transaction.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
internal class GetTransactionInfoResponse(
    val transaction: TransactionInfoData
) {
    @Serializable
    data class TransactionInfoData(
        val type: String,
        val nonce: Long,
        val round: Long,
        val epoch: Long,
        @Serializable(with = BigIntegerSerializer::class)
        val value: BigInteger,
        val sender: String,
        val receiver: String,
        val senderUsername: String?,
        val receiverUsername: String?,
        val gasPrice: Long,
        val gasLimit: Long,
        val data: String?,
        val signature: String,
        val sourceShard: Long,
        val destinationShard: Long,
        val blockNonce: Long,
        val miniBlockHash: String?,
        val blockHash: String?,
        val status: String,
        val hyperblockNonce: Long?
    )

}
