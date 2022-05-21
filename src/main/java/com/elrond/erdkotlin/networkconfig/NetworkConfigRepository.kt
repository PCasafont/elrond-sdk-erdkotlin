package com.elrond.erdkotlin.networkconfig

import com.elrond.erdkotlin.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.networkconfig.models.NetworkConfig

internal class NetworkConfigRepository(
    private val elrondProxy: ElrondProxy
) {
    fun getNetworkConfig(): NetworkConfig {
        val response = elrondProxy.getNetworkConfig()
        return requireNotNull(response.data).config.toDomain()
    }
}
