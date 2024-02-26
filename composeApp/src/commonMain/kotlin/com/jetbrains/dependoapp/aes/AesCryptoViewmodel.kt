package com.jetbrains.dependoapp.aes

import com.jetbrains.dependoapp.decrypt
import com.jetbrains.dependoapp.encrypt
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AesCryptoViewmodel():ViewModel() {

    fun encryptRequest(data:String,callBack:(String)->Unit){
        viewModelScope.launch(Dispatchers.Default) {
            val result  = encrypt(data)
            callBack(result)
        }
    }
    fun decryptResponse(data:String,callBack:(String)->Unit){
        viewModelScope.launch(Dispatchers.Default) {
            val result  = decrypt(data)
            callBack(result)
        }
    }
}