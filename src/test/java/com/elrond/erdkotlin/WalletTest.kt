package com.elrond.erdkotlin

import com.elrond.erdkotlin.wallet.Wallet
import com.elrond.erdkotlin.wallet.createWalletFromMnemonic
import com.elrond.erdkotlin.wallet.generateWalletMnemonic
import io.kotest.matchers.shouldBe
import org.junit.Test

import org.junit.Assert.assertEquals

class WalletTest {

    @Test
    fun generateMnemonic() {
        val words = generateWalletMnemonic()
        words.size shouldBe 24
    }

    @Test
    fun deriveFromMnemonic() {
        // Emotion spare
        var mnemonic =
            "emotion spare multiply lecture rude machine raise radio ability doll depend equip pass ghost cabin delay birth opera shoe force any slow fluid old"
        var expectedPrivateKey = "4d6fbfd1fa028afee050068f08c46b95754fd27a06f429b308ba326fff094349"
        var expectedPublicKey = "10afb6ed5c730bff355db7958ae19a466d4c78be8780db271192eec1b266c2a4"
        var wallet: Wallet = createWalletFromMnemonic(mnemonic, 0)
        wallet.privateKeyHex shouldBe expectedPrivateKey
        wallet.publicKeyHex shouldBe expectedPublicKey

        // Real reveal
        mnemonic =
            "real reveal sausage puppy artefact chapter original enough cinnamon run pledge awake fall double antenna admit keep melody celery since hood hurry achieve fee"
        expectedPrivateKey = "33306aa0bf13a02057ece15e07dc8e9cfff2b77147d5a007d205d782fc90e362"
        expectedPublicKey = "fd41097cdc0462dfb4fc96c1f04410ad13407e012290c73bbb85d8a96d28aa22"
        wallet = createWalletFromMnemonic(mnemonic, 0)
        wallet.privateKeyHex shouldBe expectedPrivateKey
        wallet.publicKeyHex shouldBe expectedPublicKey
    }

}
