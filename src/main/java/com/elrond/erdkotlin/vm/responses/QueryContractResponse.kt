package com.elrond.erdkotlin.vm.responses

import com.elrond.erdkotlin.utils.BigIntegerSerializer
import com.elrond.erdkotlin.vm.query.QueryContractOutput
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
internal data class QueryContractResponse(
    val data: Data
) {
    @Serializable
    data class Data(
        val returnData: List<String>?, // ex: ["Aw=="]
        val returnCode: String, // ex: "ok"
        val returnMessage: String?,
        @Serializable(with = BigIntegerSerializer::class)
        val gasRemaining: BigInteger,
        @Serializable(with = BigIntegerSerializer::class)
        val gasRefund: BigInteger,
        val outputAccounts: Map<String, QueryContractOutput.OutputAccount>?,

        // Keeping those as placeholders for future development
        // https://github.com/ElrondNetwork/elrond-go/blob/master/core/vmcommon/output.go
        //private val deletedAccounts: Any?,
        //private val touchedAccounts: Any?,
        //private val logs: Any?
    )
}
