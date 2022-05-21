package com.elrond.erdkotlin.networkconfig

import com.elrond.erdkotlin.networkconfig.models.NetworkConfig

internal interface NetworkConfigRepository {

    fun getNetworkConfig(): NetworkConfig
}
