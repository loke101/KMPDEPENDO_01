package com.jetbrains.dependoapp.AppPref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.withContext

interface AppPreferences  {
    suspend fun saveStringValue(key:String,value:String)
    suspend fun getStringValue(key: String):Flow<String>
    suspend fun saveBooleanValue(key:String,value: Boolean)
     suspend fun getBooleanValue(key: String):Flow<Boolean?>
}

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
):AppPreferences {

    override suspend fun saveStringValue(key: String, value: String) {
            val prefKey = stringPreferencesKey(key)
        dataStore.edit {preferences->
                preferences[prefKey] = value
            }
    }






    override suspend fun saveBooleanValue(key: String, value: Boolean) {
            val prefKey = booleanPreferencesKey(key)
           dataStore.edit {preferences->
                preferences[prefKey] =value
            }
    }
    override suspend fun getStringValue(key: String): Flow<String> {
        val prefKey = stringPreferencesKey(key)
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { pref->
            pref[prefKey]?:""
        }.flowOn(Dispatchers.IO)
    }
    override suspend fun getBooleanValue(key: String): Flow<Boolean?> {
            val prefKey = booleanPreferencesKey(key)
           return dataStore.data.catch {
               emit(emptyPreferences())
           }.map { pref->
               pref[prefKey]?:false
           }.flowOn(Dispatchers.IO)
    }
}