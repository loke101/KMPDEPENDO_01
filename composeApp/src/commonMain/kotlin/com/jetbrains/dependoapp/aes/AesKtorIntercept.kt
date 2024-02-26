package com.jetbrains.dependoapp.aes

import com.jetbrains.dependoapp.decrypt
import io.github.aakira.napier.Napier
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.statement.bodyAsText
import io.ktor.util.InternalAPI
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json


@OptIn(InternalSerializationApi::class, DelicateCoroutinesApi::class, InternalAPI::class)
    val ResponseTimePlugin = createClientPlugin("ResponseTimePlugin") {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

//        onRequest { request, content ->
//            val encryptedRequestBody = encrypt(request.body.toString())
//            Napier.i(encryptedRequestBody)
//            request.apply {
//                method = HttpMethod.Post // or any other method you're using
//                body = encryptedRequestBody
//            }
//        }

        onResponse { response ->
            val responseText = response.bodyAsText()
            response.apply {
//                println("pn :${json.parseToJsonElement(decrypt(response.bodyAsText()))  }")
                json.decodeFromString<String>(decrypt(responseText)).also {
                    Napier.i(it.toString())
                }
            }


        }
    }
private fun printHeader(entry: Map.Entry<String, List<String>>) {
    var headerString = entry.key + ": "
    entry.value.forEach { headerValue ->
        headerString += "${headerValue};"
    }
    println("-> $headerString")
}