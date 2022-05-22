package com.elrond.erdkotlin.api

import com.elrond.erdkotlin.ProxyRequestException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

internal class ElrondClient(
    var url: String
) {
    private val httpClient: HttpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    suspend inline fun <reified T> doGet(resourceUrl: String): T {
        val response: ResponseBase<T> = httpClient.get("$url/$resourceUrl").body()
        return response.extractData()
    }

    suspend inline fun <reified T, reified B> doPost(resourceUrl: String, body: B): T {
        val response: ResponseBase<T> = httpClient.post("$url/$resourceUrl") {
            setBody(body)
        }.body()
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
