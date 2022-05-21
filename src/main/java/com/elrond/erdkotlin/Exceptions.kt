package com.elrond.erdkotlin

sealed class KnownException(message: String? = null) : Exception(message)

sealed class AddressException(message: String? = null) : KnownException(message)

class CannotCreateAddressException(input: Any) : AddressException("Cannot create address from: $input")

class CannotCreateBech32AddressException(input: Any) : AddressException("Cannot create bech32 address from: $input")

class BadAddressHrpException : AddressException()

class EmptyAddressException : AddressException()

class CannotConvertBitsException : AddressException()

class InvalidCharactersException : AddressException()

class InconsistentCasingException : AddressException()

class MissingAddressHrpException : AddressException()

class CannotGenerateMnemonicException : KnownException()

class CannotDeriveKeysException : KnownException()

class CannotSerializeTransactionException : KnownException()

class CannotSignTransactionException : KnownException()

class ProxyRequestException(message: String? = null) : KnownException(message)
