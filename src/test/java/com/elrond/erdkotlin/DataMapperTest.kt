package com.elrond.erdkotlin

import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.vm.responses.QueryContractResponse
import io.kotest.matchers.shouldBe
import org.bouncycastle.util.encoders.Base64
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigInteger

class DataMapperTest {

    @Test
    fun `query return data must be well decoded and formatted`() {
        val oneHundredBase64 = String(Base64.encode(arrayOf(100.toByte()).toByteArray()))

        val queryResponseData = QueryContractResponse.Data(
            returnData = listOf(oneHundredBase64),
            returnCode = "",
            returnMessage = null,
            gasRemaining = BigInteger.ZERO,
            gasRefund = BigInteger.ZERO,
            outputAccounts = null,
            //deletedAccounts = null,
            //touchedAccounts = null,
            //logs = null
        )

        val returnedData = queryResponseData.toDomain().returnData?.first()!!

        returnedData.asBase64 shouldBe "ZA=="
        returnedData.asHex shouldBe "64"
        returnedData.asString shouldBe "d"
        returnedData.asBigInt shouldBe 100.toBigInteger()
    }
}
