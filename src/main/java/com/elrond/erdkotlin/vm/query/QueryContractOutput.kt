package com.elrond.erdkotlin.vm.query

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

data class QueryContractOutput(
    val returnData: List<ReturnData>?,
    val returnCode: String,
    val returnMessage: String?,
    val gasRemaining: BigInteger,
    val gasRefund: BigInteger,
    val outputAccounts: Map<String, OutputAccount>?
) {
    data class ReturnData(
        val asBase64: String,
        val asString: String,
        val asHex: String,
        val asBigInt: BigInteger
    )

    @Serializable
    data class OutputAccount(
        val address: String,
        val nonce: Long,
        @Serializable(with = BigIntegerSerializer::class)
        val balance: BigInteger?,
        @Serializable(with = BigIntegerSerializer::class)
        val balanceDelta: BigInteger,
        val storageUpdates: Map<String, StorageUpdate>?,
        val callType: Long,

        // Keeping those as placeholders for future development
        // https://github.com/ElrondNetwork/elrond-go/blob/master/core/vmcommon/output.go
        //val code: Any?,
        //val codeMetadata: Any?,
        //val data: Any?,
        //val gasLimit: Any?,
    ){
        @Serializable
        data class StorageUpdate(
            val offset: String
        )
    }
}
