
## Elrond Kotlin SDK

This branch is based on the kotlin implementation of Elrond SDK, but closer to the Kotlin ecosystem and style.

## Usage

Here is an example for sending a transaction.
```
val erdSdk = ErdSdk()

// Create a wallet from mnemonics
val wallet = createWalletFromMnemonic("...", 0)

// Get information related to this address (ie: balance and nonce)
val account = erdSdk.getAccount(wallet.publicKeyHex.asHexAddress())

// Get the network informations
val networkConfig = erdSdk.getNetworkConfig()

// Create the transaction object
val transaction = Transaction(
    sender = account.address,
    receiver = "...".asHexAddress(),
    value = 1000000000000000000.toBigInteger(), // 1 xEGLD
    data = "Elrond rocks !",
    chainID = networkConfig.chainID,
    gasPrice = networkConfig.minGasPrice,
    gasLimit = networkConfig.minGasLimit,
    nonce = account.nonce
)

// Send transaction.
// Signature is handled internally
val sentTransaction = erdSdk.sendTransaction(transaction, wallet)
println("Transaction: ${sentTransaction.txHash}")
```

## Configuration
```
// default value is ProviderUrl.DevNet
val erdSdk = ErdSdk(ProviderUrl.MainNet)
```
