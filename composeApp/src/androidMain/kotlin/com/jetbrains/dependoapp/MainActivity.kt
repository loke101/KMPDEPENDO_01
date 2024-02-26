package com.jetbrains.dependoapp

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.initialize
import com.google.firebase.messaging.FirebaseMessaging
import com.jetbrains.dependoapp.AppPref.ViewModel2
import com.jetbrains.dependoapp.CryptoUtils.decrypt
import com.jetbrains.dependoapp.CryptoUtils.encrypt
import com.jetbrains.dependoapp.LocalCiphers.localCipher
import com.liftric.kvault.KVault
import com.rider.shipatpl.R
import dev.gitlive.firebase.initialize
import dev.icerock.moko.mvvm.getViewModel
import dev.icerock.moko.permissions.PermissionsController
import diglol.crypto.AesCbc

import diglol.crypto.Error
import diglol.crypto.random.nextBytes
import diglol.encoding.encodeBase64ToString
import io.ktor.util.decodeBase64Bytes
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.launch
import okio.Utf8
import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dev.gitlive.firebase.Firebase.initialize(this@MainActivity)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
        setContent {
            App()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {

}


object CryptoUtils {
        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun encrypt(plaintext: ByteArray): ByteArray {
            val keySpec = SecretKeySpec("b0a7c6fbcdfb20b1512e8a4a6e5e7b5f".toByteArray(), "AES")
            try {
                val cipher = localCipher.get()
                val realIv = "d1e53f2aeb0480c4".toByteArray() ?: nextBytes(AesCbc.IV_SIZE)
                cipher?.init(Cipher.ENCRYPT_MODE, keySpec, IvParameterSpec(realIv))
                return cipher?.doFinal(plaintext)!!
            } catch (e: Exception) {
                throw Error("Aes cbc encrypt error", e)
            }

        }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun decrypt(ciphertext: ByteArray): ByteArray {
        val secretKey = SecretKeySpec("b0a7c6fbcdfb20b1512e8a4a6e5e7b5f".toByteArray(), "AES")
        val iv = "d1e53f2aeb0480c4".toByteArray()
        try {
            val cipher = localCipher.get()
            cipher?.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                IvParameterSpec(iv)
            )
            return cipher.doFinal(ciphertext)!!
        } catch (e: Exception) {
            throw Error("Aes cbc decrypt error", e)
        }
    }
}

object LocalCiphers {
    val localCipher = object : ThreadLocal<Cipher>() {
        override fun initialValue(): Cipher {
            return Cipher.getInstance("AES/CBC/PKCS5Padding")
        }
    }
}