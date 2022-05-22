package com.elrond.erdkotlin

import com.elrond.erdkotlin.dns.checkUsername
import io.kotest.matchers.shouldBe
import org.junit.Test

class CheckUsernameUsecaseTest {

    @Test
    fun `valid alphanumeric`() {
        val isUsernameValid = "abc123.elrond".checkUsername()
        isUsernameValid shouldBe true
    }

    @Test
    fun `valid 3 chars`() {
        val isUsernameValid = "abc.elrond".checkUsername()
        isUsernameValid shouldBe true
    }

    @Test
    fun `valid 25 chars`() {
        val isUsernameValid = "abcdefghiklmnopqrstuvwxyz.elrond".checkUsername()
        isUsernameValid shouldBe true
    }

    @Test
    fun `wrong less than 3 chars`() {
        val isUsernameValid = "ab.elrond".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong more than 25 chars`() {
        val isUsernameValid = "abcdefghiklmnopqrstuvwxyz1.elrond".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong maj not allowed`() {
        val isUsernameValid = "ABC.elrond".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong elrond suffix is required`() {
        val isUsernameValid = "abcde".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong elrond must be the suffix`() {
        val isUsernameValid = "abc.elrond.com".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong underscore not allowed`() {
        val isUsernameValid = "abc_123.elrond".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong dash not allowed`() {
        val isUsernameValid = "abc-123.elrond".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong @ not allowed`() {
        val isUsernameValid = "abc@123.elrond".checkUsername()
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong space not allowed`() {
        val isUsernameValid = "abc 123.elrond".checkUsername()
        isUsernameValid shouldBe false
    }
}
