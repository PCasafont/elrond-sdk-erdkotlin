package com.elrond.erdkotlin


import com.elrond.erdkotlin.wallet.Address
import com.elrond.erdkotlin.wallet.asBech32Address
import com.elrond.erdkotlin.wallet.asHexAddress
import com.elrond.erdkotlin.wallet.isValidBech32
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
        aliceBech32.asBech32Address().hex shouldBe aliceHex
        bobBech32.asBech32Address().hex shouldBe bobHex
        aliceHex.asHexAddress().bech32() shouldBe aliceBech32
        bobHex.asHexAddress().bech32() shouldBe bobBech32
    }

    @Test
    fun shouldNotCreate() {
        shouldThrow<CannotCreateAddressException> { "F".asHexAddress() }
        shouldThrow<CannotCreateAddressException> { "FOOBAR".asHexAddress() }
        shouldThrow<CannotCreateBech32AddressException>{
            "erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldy".asBech32Address()
        }
    }

    @Test
    fun isValidBech32() {
        "erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz".isValidBech32() shouldBe true
        "erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r".isValidBech32() shouldBe true
        "ERD1CUX02ZERSDE0L7HHKLZHYWCXK4U9N4PY5TDXYX7VRVHNZA2R4GMQ4VW35R".isValidBech32() shouldBe true

        "".isValidBech32() shouldBe false
        "erd1.".isValidBech32() shouldBe false
        "tbnb1uzqphymsp539lc8s2pucqwdphzydmr2a76jm8w".isValidBech32() shouldBe false
        "erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldy".isValidBech32() shouldBe false
        "bad1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz".isValidBech32() shouldBe false
        "erd1CUX02ZERSDE0L7HHKLZHYWCXK4U9N4PY5TDXYX7VRVHNZA2R4GMQ4VW35R".isValidBech32() shouldBe false
    }
}
