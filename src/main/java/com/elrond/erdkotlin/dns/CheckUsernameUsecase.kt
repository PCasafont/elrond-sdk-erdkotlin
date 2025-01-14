package com.elrond.erdkotlin.dns

// This usecase is visible by the host app as it can be useful for example to validate a text field
// it is used internally as well
fun String.checkUsername(shouldThrow: Boolean = false): Boolean {
    val isUsernameValid = matches("^[a-z0-9]{3,25}.elrond$".toRegex())
    if (!isUsernameValid && shouldThrow) {
        throw IllegalArgumentException(
            "username must be" +
                    " alphanumeric only," +
                    " lowercase," +
                    " between 3 and 25 characters (without .elrond suffix)," +
                    " must end with .elrond suffix"
        )
    }
    return isUsernameValid
}