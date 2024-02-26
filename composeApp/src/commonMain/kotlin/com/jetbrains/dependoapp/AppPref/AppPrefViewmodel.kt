package com.jetbrains.dependoapp.AppPref

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch

class AppPrefViewmodel(private val appPreferences: AppPreferences):ViewModel() {


}
internal class ViewModel2(val permissionsController: PermissionsController): ViewModel() {
    fun onPhotoPressed() {
        viewModelScope.launch {
            try {
                permissionsController.providePermission(Permission.GALLERY)
                // Permission has been granted successfully.
            } catch(deniedAlways: DeniedAlwaysException) {
                // Permission is always denied.
            } catch(denied: DeniedException) {
                // Permission was denied.
            }
        }
    }
}