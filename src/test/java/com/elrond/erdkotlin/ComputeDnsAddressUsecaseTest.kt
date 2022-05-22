package com.elrond.erdkotlin

import com.elrond.erdkotlin.account.Account
import com.elrond.erdkotlin.dns.computeAddress
import com.elrond.erdkotlin.dns.computeDnsAddress
import com.elrond.erdkotlin.dns.nameHash
import com.elrond.erdkotlin.dns.toUInt32ByteArray
import com.elrond.erdkotlin.wallet.Address
import com.elrond.erdkotlin.wallet.asBech32Address
import io.kotest.matchers.shouldBe
import org.junit.Assert.assertEquals
import org.junit.Test

class ComputeDnsAddressUsecaseTest {

    @Test
    fun nameHash() {
        val nameHash = "alex.elrond".nameHash()
        nameHash.joinToString(separator = "") {
            String.format("%02X", it)
        } shouldBe "560DD600F74E220A4870CBFFB9143F30617754DFFBAEBF7B4C499EDEAE8B673D"
    }

    @Test
    fun longToUInt32ByteArray() {
        val byteArray = 123211089L.toUInt32ByteArray(8)
        byteArray.toList() shouldBe byteArrayOf(81, 13, 88, 7, 0, 0, 0, 0).toList()
    }

    @Test
    fun computeAddress() {
        val address = Account(
            address = "erd1qyqszqgpqyqszqgpqyqszqgpqyqszqgpqyqszqgpqyqszqgpqq7se967nn".asBech32Address()
        ).computeAddress()
        address.bech32() shouldBe "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v"
    }

    @Test
    fun `compute dns address bech32 from name`() {
        val dnsAddress = computeDnsAddress("alex.elrond")
        dnsAddress.bech32() shouldBe "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v"
    }

    @Test
    fun `compute dns address hex from name`() {
        val dnsAddress = computeDnsAddress("alex.elrond")
        dnsAddress.hex shouldBe "000000000000000005005734f5e901084fc20d7c5cd0af58a0e5aaa33b40003d"
    }

    @Test
    fun `compute dns address bech32 from shardId`() {
        val dnsAddress = computeDnsAddress(61)
        dnsAddress.bech32() shouldBe "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v"
    }

    @Test
    fun `compute dns address hex from shardId`() {
        val dnsAddress = computeDnsAddress(61)
        dnsAddress.hex shouldBe "000000000000000005005734f5e901084fc20d7c5cd0af58a0e5aaa33b40003d"
    }
}
