package com.elrond.erdkotlin

import com.elrond.erdkotlin.account.Account
import com.elrond.erdkotlin.wallet.Address
import io.kotest.matchers.shouldBe
import org.junit.Assert.assertEquals
import org.junit.Test

class ComputeDnsAddressUsecaseTest {

    private val computeDnsAddressUsecase = ErdSdk.computeDnsAddressUsecase()

    @Test
    fun nameHash() {
        val nameHash = computeDnsAddressUsecase.nameHash("alex.elrond")
        nameHash.joinToString(separator = "") {
            String.format("%02X", it)
        } shouldBe "560DD600F74E220A4870CBFFB9143F30617754DFFBAEBF7B4C499EDEAE8B673D"
    }

    @Test
    fun longToUInt32ByteArray() {
        val byteArray = computeDnsAddressUsecase.longToUInt32ByteArray(123211089L, 8)
        byteArray.toList() shouldBe byteArrayOf(81, 13, 88, 7, 0, 0, 0, 0).toList()
    }

    @Test
    fun computeAddress() {
        val address = computeDnsAddressUsecase.computeAddress(
            Account(
                address = Address.fromBech32("erd1qyqszqgpqyqszqgpqyqszqgpqyqszqgpqyqszqgpqyqszqgpqq7se967nn")
            )
        )
        address.bech32() shouldBe "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v"
    }

    @Test
    fun `compute dns address bech32 from name`() {
        val dnsAddress = computeDnsAddressUsecase.execute("alex.elrond")
        dnsAddress.bech32() shouldBe "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v"
    }

    @Test
    fun `compute dns address hex from name`() {
        val dnsAddress = computeDnsAddressUsecase.execute("alex.elrond")
        dnsAddress.hex shouldBe "000000000000000005005734f5e901084fc20d7c5cd0af58a0e5aaa33b40003d"
    }

    @Test
    fun `compute dns address bech32 from shardId`() {
        val dnsAddress = computeDnsAddressUsecase.execute(61)
        dnsAddress.bech32() shouldBe "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v"
    }

    @Test
    fun `compute dns address hex from shardId`() {
        val dnsAddress = computeDnsAddressUsecase.execute(61)
        dnsAddress.hex shouldBe "000000000000000005005734f5e901084fc20d7c5cd0af58a0e5aaa33b40003d"
    }
}
