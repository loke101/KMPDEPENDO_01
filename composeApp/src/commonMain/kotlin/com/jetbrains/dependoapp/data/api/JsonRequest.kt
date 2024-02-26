package com.jetbrains.dependoapp.data.api

import com.jetbrains.dependoapp.decrypt
import com.jetbrains.dependoapp.encrypt
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.post
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.jvm.JvmInline

/**
 * Author Lokendra Bohara
 * Constructs a [JsonObject] from the given parameters.
 * @param parameters The parameter-value pairs to include in the JsonObject.
 * @return The constructed JsonObject.
 */
internal fun createJsonObject(vararg parameters: Pair<String, Any>): JsonObject {
    return buildJsonObject {
        parameters.forEach { (key, value) ->
            when (value) {
                is String -> put(key, value)
                is Int -> put(key, value)
                is Boolean ->put(key,value)
                is Long->put(key,value)
                else -> throw IllegalArgumentException("Unsupported type: $value")
            }
        }
    }
}
/**
 * Creates an encrypted request body from the given [JsonObject].
 *Author : Lokendra Bohara
 * @param jsonObject The JsonObject to encrypt.
 * @return The encrypted request body as a String.
 */
 suspend  fun  createEncryptedRequestBody(jsonObject: JsonObject): String {
    return encrypt(jsonObject.toString())
}
suspend inline fun <reified T : Any> deserializeResponse(response: String): T? {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }
    Napier.e("RESPONSE BODY TEXT :$response")
    val decryptedString = decrypt(response)
    return json.decodeFromString<T?>(decryptedString).also {
        Napier.e("RESPONSE BODY JSON :$it")
    }
}

public  class ApiClient(public val httpClient: HttpClient){
    suspend inline fun <reified T : Any> sendRequest(urls: String, jsonObject: JsonObject?=null): T? {
        try {
            val encryptedRequestBody = jsonObject?.let { createEncryptedRequestBody(it) }
            val response = httpClient.post {
                url(urls)
                contentType(ContentType.Application.Json)
                setBody(encryptedRequestBody).also {
                    Napier.e("REQUEST BODY SENT PLAIN:->$jsonObject")
                    Napier.e("REQUEST BODY SENT ENCRYPTED:-> $encryptedRequestBody")
                }
            }
            return if (response.status.isSuccess()) {
                deserializeResponse<T>(response.bodyAsText()).also {
                    Napier.e("Response Status: ${response.status}")
                }
            } else {
                null
            }
        } catch (e: Exception) {
            when (e) {
                is IOException, is SocketTimeoutException, is IllegalArgumentException -> {
                    e.printStackTrace()
                    Napier.e("Response Status: $e")
                }
                else -> {
                    e.printStackTrace()
                    Napier.e("Response Status: $e")
                }
            }
            Napier.e("Response Status: $e")
            return null
        }
    }
}
