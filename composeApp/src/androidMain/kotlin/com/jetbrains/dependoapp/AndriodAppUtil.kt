package com.jetbrains.dependoapp

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Locale
import java.util.UUID

object AndriodAppUtil {
    const val salt = "Dependo@$3cur3@2022"
    suspend fun generateRandomUUID(): String {
        return UUID.randomUUID().toString()

    }

    fun generateSha512(input: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-512")
        val digest = messageDigest.digest(input.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }

    fun generateSHA512Hash(mobile: String, randomid: String, salt: String): String {
        try {
            val combinedInput = "$mobile$randomid$salt"
            val sha512Digest = MessageDigest.getInstance("SHA-512")
            val hashBytes = sha512Digest.digest(combinedInput.toByteArray(StandardCharsets.UTF_8))
            return hashBytes.toHex().lowercase(Locale.getDefault())
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Error generating SHA-512 hash")
        }
    }

    private fun ByteArray.toHex(): String {
        val hexChars = "0123456789ABCDEF"
        val result = StringBuilder(this.size * 2)

        for (byte in this) {
            val octet = byte.toInt()
            result.append(hexChars[octet shr 4 and 0x0F])
            result.append(hexChars[octet and 0x0F])
        }

        return result.toString()
    }
}