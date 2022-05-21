package com.elrond.erdkotlin.networkconfig

class GetNetworkConfigUsecase internal constructor(
    private val networkConfigRepository: NetworkConfigRepository
) {
    fun execute() = networkConfigRepository.getNetworkConfig()
}
