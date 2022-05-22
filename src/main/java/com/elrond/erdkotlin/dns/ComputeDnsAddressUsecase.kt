package com.elrond.erdkotlin.dns

import com.elrond.erdkotlin.account.Account
import com.elrond.erdkotlin.wallet.Address
import com.elrond.erdkotlin.wallet.asHexAddress
import org.bouncycastle.jcajce.provider.digest.Keccak
import org.bouncycastle.util.encoders.Hex
import java.nio.charset.StandardCharsets

private val initialDnsAddress = ByteArray(32) { 1 }
private const val shardIdentiferLen = 2

// Returns the compatible DNS address
//
// The implementation is based on
// - https://github.com/ElrondNetwork/elrond-sdk/blob/52a0ba4414f96068a280b936bd3b456d0b2dd557/erdpy/dns.py
// - https://github.com/ElrondNetwork/elrond-sdk/blob/52a0ba4414f96068a280b936bd3b456d0b2dd557/erdpy/contracts.py
fun computeDnsAddress(username: String): Address {
    username.checkUsername(shouldThrow = true)
    val hash = username.nameHash()
    val shardId = hash.last()
    return computeDnsAddress(shardId)
}

// Based on
// https://github.com/ElrondNetwork/elrond-sdk/blob/52a0ba4414f96068a280b936bd3b456d0b2dd557/erdpy/dns.py#L91
fun computeDnsAddress(shardId: Byte): Address {
    val deployerPubkeyPrefix = initialDnsAddress.sliceArray(
        0 until initialDnsAddress.size - shardIdentiferLen
    )
    val deployerPubkey = deployerPubkeyPrefix + 0 + shardId
    val account = Account(
        address = String(Hex.encode(deployerPubkey)).asHexAddress(),
        nonce = 0
    )
    return account.computeAddress()
}

// Based on
// https://github.com/ElrondNetwork/elrond-sdk/blob/52a0ba4414f96068a280b936bd3b456d0b2dd557/erdpy/dns.py#L64
fun String.nameHash(): ByteArray {
    val digest = Keccak.Digest256()
    return digest.digest(toByteArray(StandardCharsets.UTF_8))
}

// Based on
// https://github.com/ElrondNetwork/elrond-sdk/blob/d896fb777ca354374d93fec7723adbe28ea3f580/erdpy/contracts.py#L51
fun Account.computeAddress(): Address {
    // 8 bytes of zero + 2 bytes for VM type + 20 bytes of hash(owner) + 2 bytes of shard(owner)
    val ownerBytes = address.pubkey()
    val nonceBytes = nonce.toUInt32ByteArray(8)
    val bytesToHash = ownerBytes + nonceBytes
    val ownerHash = Keccak.Digest256().digest(bytesToHash)
    val dnsAddress =
        ByteArray(8) { 0 } + 5 + 0 + ownerHash.slice(10 until 30) + ownerBytes[30] + ownerBytes[31]
    return String(Hex.encode(dnsAddress)).asHexAddress()
}

// litte endian implementation
fun Long.toUInt32ByteArray(length: Int): ByteArray {
    val bytes = ByteArray(length)
    for (i in 0 until length) {
        bytes[i] = ((this ushr (i * 8)) and 0xFFFF).toByte()
    }
    return bytes
}