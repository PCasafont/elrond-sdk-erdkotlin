package com.elrond.erdkotlin.networkconfig

class GetNetworkConfigUsecase internal constructor(
    private val networkConfigRepository: NetworkConfigRepository
) {
    suspend fun execute() = networkConfigRepository.getNetworkConfig()
}
