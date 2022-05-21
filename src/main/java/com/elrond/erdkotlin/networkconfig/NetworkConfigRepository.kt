package com.elrond.erdkotlin.networkconfig

import com.elrond.erdkotlin.Exceptions
import com.elrond.erdkotlin.networkconfig.models.NetworkConfig
import java.io.IOException

internal interface NetworkConfigRepository {

    @Throws(IOException::class, Exceptions.ProxyRequestException::class)
    fun getNetworkConfig(): NetworkConfig
}
