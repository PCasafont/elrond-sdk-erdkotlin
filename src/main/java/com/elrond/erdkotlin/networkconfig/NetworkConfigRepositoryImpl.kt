package com.elrond.erdkotlin.networkconfig

import com.elrond.erdkotlin.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.networkconfig.models.NetworkConfig

internal class NetworkConfigRepositoryImpl(
    private val elrondProxy: ElrondProxy
) : NetworkConfigRepository {

    override fun getNetworkConfig(): NetworkConfig {
        val response = elrondProxy.getNetworkConfig()
        return requireNotNull(response.data).config.toDomain()
    }

}
