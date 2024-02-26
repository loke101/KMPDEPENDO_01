package com.jetbrains.dependoapp

import android.util.Log
import com.jetbrains.dependoapp.AndriodAppUtil.generateRandomUUID
import com.jetbrains.dependoapp.AndriodAppUtil.generateSHA512Hash
import com.jetbrains.dependoapp.AndriodAppUtil.salt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt
import java.security.MessageDigest

actual suspend fun genrateHasPassword(password:String):String{
   return withContext(Dispatchers.Default){
       BCrypt.hashpw(password, BCrypt.gensalt(12)).trim()
   }
}
actual suspend fun generateSha512(input: String): String {
    return withContext(Dispatchers.Default){
        val messageDigest = MessageDigest.getInstance("SHA-512")
        val digest = messageDigest.digest(input.toByteArray())
        digest.joinToString("") { "%02x".format(it) }
    }

}
actual suspend fun genrateXsignId(mobile:String,randomId:String):String{
    Log.e("Xsign", "genrateXsignId: $randomId", )
    return withContext(Dispatchers.Default){
        Log.e("Xsign", "genrateXsignId: $randomId ,$mobile ,$salt", )
        generateSHA512Hash(mobile,randomId,salt)
    }
}
actual suspend fun uUIDGenerator():String{
    return withContext(Dispatchers.Default){
        generateRandomUUID()
    }
}
