
## Elrond Kotlin SDK

This branch is based on the kotlin implementation of Elrond SDK, but closer to the Kotlin ecosystem and style.

## Usage

Here is an example for sending a transaction.
```
val erdSdk = ErdSdk()

// Create a wallet from mnemonics
val wallet = createWalletFromMnemonic("...", 0)

// Get information related to this address (ie: balance and nonce)
val account = erdSdk.getAccountUsecase().execute(wallet.publicKeyHex.asHexAddress())

// Get the network informations
val networkConfig = erdSdk.getNetworkConfigUsecase().execute()

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
val sentTransaction = erdSdk.sendTransactionUsecase().execute(transaction, wallet)
println("Transaction: ${sentTransaction.txHash}")
```

## Usecases list

##### API
| Usecase  | Endpoint  |
| ------------- | ------------- |
| GetAccountUsecase  |  [GET address/:bech32Address](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address) |
| GetAddressBalanceUsecase  | [GET address/:bech32Address/balance](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address-balance) |
| GetAddressNonceUsecase  | [GET address/:bech32Address/nonce](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address-nonce) |
| GetAddressTransactionsUsecase  | [GET address/:bech32Address/transactions](https://docs.elrond.com/sdk-and-tools/rest-api/addresses/#get-address-transactions) |
| GetTransactionInfoUsecase  | [GET transaction/:txHash](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#get-transaction) |
| GetTransactionStatusUsecase  | [GET transaction/:txHash/status](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#get-transaction-status) |
| SendTransactionUsecase  | [POST transaction/send](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#send-transaction) |
| EstimateCostOfTransactionUsecase  | [POST transaction/cost](https://docs.elrond.com/sdk-and-tools/rest-api/transactions/#estimate-cost-of-transaction) |
| GetNetworkConfigUsecase  | [GET network/config](https://docs.elrond.com/sdk-and-tools/rest-api/network/#get-network-configuration) |
| QueryContractUsecase  | [POST vm-values/query](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#compute-output-of-pure-function) |
| QueryContractHexUsecase  | [POST vm-values/hex](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#compute-hex-output-of-pure-function) |
| QueryContractStringUsecase  | [POST vm-values/string](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#compute-string-output-of-pure-function) |
| QueryContractIntUsecase  | [POST vm-values/int](https://docs.elrond.com/sdk-and-tools/rest-api/virtual-machine/#get-integer-output-of-pure-function) |

#### Contract
| Usecase  | Description  |
| ------------- | ------------- |
| CallContractUsecase  | Interact with a Smart Contract (execute function): equivalent to [`erdpy contract call`](https://docs.elrond.com/sdk-and-tools/erdpy/erdpy/) |

##### DNS
| Usecase  | Description  |
| ------------- | ------------- |
| RegisterDnsUsecase  | Send a register transaction to the appropriate DNS contract from given user and with given name: equivalent to [`erdpy dns register`](https://docs.elrond.com/sdk-and-tools/erdpy/erdpy/) |
| GetDnsRegistrationCostUsecase  | Gets the registration cost from a DNS smart contract: equivalent to [`erdpy dns registration-cost`](https://docs.elrond.com/sdk-and-tools/erdpy/erdpy/) |
| CheckUsernameUsecase  | Can be useful for validating a text field before calling `RegisterDnsUsecase `|


## Configuration
```
// default value is ProviderUrl.DevNet
val erdSdk = ErdSdk(ProviderUrl.MainNet)
```
