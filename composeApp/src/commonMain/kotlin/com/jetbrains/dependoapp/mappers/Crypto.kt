package com.jetbrains.dependoapp.mappers


import diglol.crypto.AesCbc
import diglol.crypto.AesCbc.Companion.IV_SIZE
import diglol.crypto.Error
import diglol.crypto.internal.toInt
import io.ktor.utils.io.core.toByteArray
object Crypto {
    private val keySpec = "b0a7c6fbcdfb20b1512e8a4a6e5e7b5f".toByteArray()
    private val realIv = "d1e53f2aeb0480c4".toByteArray()
    private val aesCbc = AesCbc(keySpec, realIv)
    suspend fun encrypt(plainTextInput: String): ByteArray {
        try {
            val result = aesCbc.encrypt(plainTextInput.toByteArray())
            return result.copyOfRange(IV_SIZE, result.size)
        } catch (e: Exception) {
            throw Error("Aes cbc encrypt error", e)
        }
    }

    suspend fun decrypt(enString: ByteArray): ByteArray {
        try {
            val result = aesCbc.decrypt(enString)
            return result
        } catch (e: Exception) {
            throw Error("Aes cbc encrypt error", e)
        }
    }

}

