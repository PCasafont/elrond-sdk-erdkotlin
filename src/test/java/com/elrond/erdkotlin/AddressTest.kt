package com.elrond.erdkotlin


import com.elrond.erdkotlin.wallet.Address
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Assert.*
import org.junit.Test


class AddressTest {

    @Test
    fun shouldCreate() {
        val aliceBech32 = "erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz"
        val bobBech32 = "erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r"
        val aliceHex = "fd691bb5e85d102687d81079dffce842d4dc328276d2d4c60d8fd1c3433c3293"
        val bobHex = "c70cf50b238372fffaf7b7c5723b06b57859d424a2da621bcc1b2f317543aa36"
        Address.fromBech32(aliceBech32).hex shouldBe aliceHex
        Address.fromBech32(bobBech32).hex shouldBe bobHex
        Address.fromHex(aliceHex).bech32() shouldBe aliceBech32
        Address.fromHex(bobHex).bech32() shouldBe bobBech32
    }

    @Test
    fun shouldNotCreate() {
        shouldThrow<CannotCreateAddressException> { Address.fromHex("F") }
        shouldThrow<CannotCreateAddressException> {
            Address.fromHex(
                "FOOBAR"
            )
        }
        shouldThrow<CannotCreateBech32AddressException>{
            Address.fromBech32(
                "erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldy"
            )
        }
    }

    @Test
    fun isValidBech32() {
        Address.isValidBech32("erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz") shouldBe true
        Address.isValidBech32("erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r") shouldBe true
        Address.isValidBech32("ERD1CUX02ZERSDE0L7HHKLZHYWCXK4U9N4PY5TDXYX7VRVHNZA2R4GMQ4VW35R") shouldBe true
        Address.isValidBech32("") shouldBe false
        Address.isValidBech32("erd1.") shouldBe false
        Address.isValidBech32("tbnb1uzqphymsp539lc8s2pucqwdphzydmr2a76jm8w") shouldBe false
        Address.isValidBech32("erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldy") shouldBe false
        Address.isValidBech32("bad1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz") shouldBe false
        Address.isValidBech32("erd1CUX02ZERSDE0L7HHKLZHYWCXK4U9N4PY5TDXYX7VRVHNZA2R4GMQ4VW35R") shouldBe false
    }
}
