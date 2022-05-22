package com.elrond.erdkotlin.networkconfig

import com.elrond.erdkotlin.api.gateway.ElrondGateway
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.networkconfig.models.NetworkConfig

internal class NetworkConfigRepository(
    private val elrondGateway: ElrondGateway
) {
    suspend fun getNetworkConfig(): NetworkConfig {
        val response = elrondGateway.getNetworkConfig()
        return response.config.toDomain()
    }
}
