package com.elrond.erdkotlin.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal class ElrondApiHttpClient(
    val url: String
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

    suspend inline fun <reified T> get(resourceUrl: String): T {
        val response: T = httpClient.get("$url/$resourceUrl").body()
        return response
    }

    suspend inline fun <reified T, reified B> post(resourceUrl: String, body: B): T {
        val response: T = httpClient.post("$url/$resourceUrl") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
        return response
    }
}
