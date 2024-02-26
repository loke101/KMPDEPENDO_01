package com.jetbrains.dependoapp
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.startup.Initializer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import diglol.encoding.decodeBase64
import diglol.encoding.decodeHexToBytes
import io.ktor.client.engine.callContext
import io.ktor.util.decodeBase64Bytes
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@RequiresApi(Build.VERSION_CODES.O)
actual suspend fun encrypt(data: String):String{
    val keySpec = SecretKeySpec("b0a7c6fbcdfb20b1512e8a4a6e5e7b5f".toByteArray(), "AES")
    return withContext(Dispatchers.Default) {
        val cipher = JvmCiphers.localCipher.get()
        val realIv = "d1e53f2aeb0480c4".toByteArray()
        cipher?.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(realIv))
        val result = cipher?.doFinal(data.toByteArray())!!
        Base64.getEncoder().encodeToString(result).toString()
    }
}
@RequiresApi(Build.VERSION_CODES.O)
actual suspend fun decrypt(data: String):String{
    val secretKey = SecretKeySpec("b0a7c6fbcdfb20b1512e8a4a6e5e7b5f".toByteArray(), "AES")
    val iv = "d1e53f2aeb0480c4".toByteArray()
    return withContext(Dispatchers.Default){
        val cipher = JvmCiphers.localCipher.get()
        cipher?.init(
            Cipher.DECRYPT_MODE,
            secretKey,
            IvParameterSpec(iv)
        )
        val decodedString = data.replace(Regex("[^A-Za-z0-9+/=]"), "")
            val result =  cipher?.doFinal(decodedString.decodeBase64Bytes())
        result?.decodeToString().toString().trim()
    }
}

actual suspend fun getFcmToken(): String = suspendCancellableCoroutine { continuation ->
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            continuation.resume(task.result)
        } else {
            continuation.resumeWithException(task.exception ?: IllegalStateException("Fetching FCM registration token failed"))
        }
    }
}
actual fun dataStorePreferences(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
    coroutineScope: CoroutineScope,
    migrations: List<DataMigration<Preferences>>,

): DataStore<Preferences> = createDataStoreWithDefaults(
    corruptionHandler = corruptionHandler,
    migrations = migrations,
    coroutineScope = coroutineScope,
    path = {
        File(applicationContext.filesDir, "datastore/$SETTINGS_PREFERENCES").path
    }
)


object JvmCiphers {
    val localCipher = object : ThreadLocal<Cipher>() {
        override fun initialValue(): Cipher {
            return Cipher.getInstance("AES/CBC/PKCS5Padding")

        }
    }
}



