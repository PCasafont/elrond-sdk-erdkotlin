package com.elrond.erdkotlin.api.gateway

import com.elrond.erdkotlin.ProxyRequestException
import com.elrond.erdkotlin.api.ElrondApiHttpClient
import kotlinx.serialization.Serializable

internal class ElrondGatewayHttpClient(
    url: String
) {
    private val httpClient = ElrondApiHttpClient(url)

    suspend inline fun <reified T> get(resourceUrl: String): T {
        val response: ResponseBase<T> = httpClient.get(resourceUrl)
        return response.extractData()
    }

    suspend inline fun <reified T, reified B> post(resourceUrl: String, body: B): T {
        val response: ResponseBase<T> = httpClient.post(resourceUrl, body)
        return response.extractData()
    }
}

@Serializable
class ResponseBase<T>(
    private val data: T?,
    private val error: String?,
    private val code: String // ex: "successful"
) {
    fun extractData(): T {
        if (!error.isNullOrEmpty()) {
            throw ProxyRequestException(error)
        }
        if (code != "successful" || data == null) {
            throw ProxyRequestException(code)
        }

        return data
    }
}
