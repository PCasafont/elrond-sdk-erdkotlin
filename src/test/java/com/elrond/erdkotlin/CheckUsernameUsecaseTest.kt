package com.elrond.erdkotlin

import io.kotest.matchers.shouldBe
import org.junit.Assert
import org.junit.Test

class CheckUsernameUsecaseTest {

    private val checkUsernameUsecase = ErdSdk.checkUsernameUsecase()

    @Test
    fun `valid alphanumeric`() {
        val isUsernameValid = checkUsernameUsecase.execute("abc123.elrond")
        isUsernameValid shouldBe true
    }

    @Test
    fun `valid 3 chars`() {
        val isUsernameValid = checkUsernameUsecase.execute("abc.elrond")
        isUsernameValid shouldBe true
    }

    @Test
    fun `valid 25 chars`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abcdefghiklmnopqrstuvwxyz.elrond"
        )
        isUsernameValid shouldBe true
    }

    @Test
    fun `wrong less than 3 chars`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "ab.elrond"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong more than 25 chars`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abcdefghiklmnopqrstuvwxyz1.elrond"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong maj not allowed`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "ABC.elrond"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong elrond suffix is required`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abcde"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong elrond must be the suffix`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abc.elrond.com"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong underscore not allowed`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abc_123.elrond"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong dash not allowed`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abc-123.elrond"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong @ not allowed`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abc@123.elrond"
        )
        isUsernameValid shouldBe false
    }

    @Test
    fun `wrong space not allowed`() {
        val isUsernameValid = checkUsernameUsecase.execute(
            "abc 123.elrond"
        )
        isUsernameValid shouldBe false
    }
}
