package com.elrond.erdkotlin.api

import com.elrond.erdkotlin.ProxyRequestException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal class ElrondClient(
    var url: String,
    private val gson: Gson
) {
    private val httpClient: HttpClient by lazy {
        HttpClient()
    }

    suspend inline fun <reified T : ResponseBase<*>> doGet(resourceUrl: String): T {
        val responseJson: String = httpClient.get("$url/$resourceUrl") {
            contentType(ContentType.Application.Json)
        }.bodyAsText()
        val response: T = gson.fromJson(responseJson)
        response.throwIfError()
        return response
    }

    suspend inline fun <reified T : ResponseBase<*>> doPost(resourceUrl: String, json: String): T {
        val responseJson = httpClient.post("$url/$resourceUrl") {
            contentType(ContentType.Application.Json)
            setBody(json)
        }.bodyAsText()
        val response: T = gson.fromJson(responseJson)
        response.throwIfError()
        return response
    }

    private inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)!!

    class ResponseBase<T> {
        val data: T? = null
        val error: String? = null
        val code: String = "" // ex: "successful"

        fun throwIfError() {
            if (!error.isNullOrEmpty()) {
                throw ProxyRequestException(error)
            }
            if (code != "successful") {
                throw ProxyRequestException(code)
            }
        }
    }
}
