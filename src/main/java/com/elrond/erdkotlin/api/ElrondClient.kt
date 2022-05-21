package com.elrond.erdkotlin.api

import com.elrond.erdkotlin.ProxyRequestException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

internal class ElrondClient(
    var url: String,
    private val gson: Gson,
    httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
) {
    private val httpClient: OkHttpClient by lazy { httpClientBuilder.build() }

    inline fun <reified T : ResponseBase<*>> doGet(resourceUrl: String): T {
        val url = "$url/$resourceUrl"
        val request: Request = Request.Builder().url(url).build()
        val responseJson = httpClient.newCall(request).execute().use { response ->
            response.body()?.string()
        }
        val response: T? = responseJson?.let { gson.fromJson(it) }
        requireNotNull(response).throwIfError()
        return response
    }

    inline fun <reified T : ResponseBase<*>> doPost(resourceUrl: String, json: String): T {
        val url = "$url/$resourceUrl"
        val body = RequestBody.create(JSON, json)
        val request: Request = Request.Builder().url(url).post(body).build()
        val responseJson =  httpClient.newCall(request).execute().use { response ->
            response.body()?.string()
        }
        val response: T? = responseJson?.let { gson.fromJson(it) }
        requireNotNull(response).throwIfError()
        return response
    }

    private inline fun <reified T> Gson.fromJson(json: String) =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)!!

    companion object {
        private val JSON = MediaType.get("application/json; charset=utf-8")
    }

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
