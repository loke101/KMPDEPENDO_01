package com.jetbrains.dependoapp.koinDi


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jetbrains.dependoapp.AppPref.AppPrefViewmodel
import com.jetbrains.dependoapp.AppPref.AppPreferences
import com.jetbrains.dependoapp.AppPref.AppPreferencesImpl
import com.jetbrains.dependoapp.aes.AesCryptoViewmodel
import com.jetbrains.dependoapp.data.api.ApiClient

import com.jetbrains.dependoapp.data.repo.DependoRepositoryImpl
import com.jetbrains.dependoapp.data.api.ApiUtils
import com.jetbrains.dependoapp.data.api.DependoApi
import com.jetbrains.dependoapp.data.api.DependoApiImpl
import com.jetbrains.dependoapp.data.repo.DependoRepository
import com.jetbrains.dependoapp.dataStorePreferences
import com.jetbrains.dependoapp.presentation.screens.Home.HomeViewModel
import com.jetbrains.dependoapp.presentation.screens.Login.LoginViewModel

import com.jetbrains.dependoapp.presentation.screens.mPinScreen.MpinViewmodel
import com.jetbrains.dependoapp.presentation.screens.splash.SplashViewModel
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.plus
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module


private val appModule = module {
    single<DependoApi> { DependoApiImpl(get(),get()) }
    single<DependoRepository> { DependoRepositoryImpl(get()) }
    single { ApiClient(get()) }

}
private val prefModule = module {
    val mainImmediateDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
    val applicationScope = CoroutineScope(SupervisorJob() + mainImmediateDispatcher)
    val dataStore: DataStore<Preferences> = dataStorePreferences(
        corruptionHandler = null,
        coroutineScope = applicationScope + Dispatchers.IO,
        migrations = emptyList()
    )
    single<AppPreferences> {AppPreferencesImpl(dataStore)}
}
val viewModelModule = module {
    factory  { LoginViewModel(get(),get())  }
    factory  { MpinViewmodel(get(),get())  }
    factory { HomeViewModel(get()) }
    factory { AesCryptoViewmodel() }
    factory { AppPrefViewmodel(get()) }
    factory { SplashViewModel(get()) }

}
val jsonModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
}

private val networkModule = module {
    single {
        @OptIn(ExperimentalSerializationApi::class)
       HttpClient {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                },
                    contentType = ContentType.Any
                )
            }
//           install(ResponseTimePlugin)

            install(Logging) {
                level = LogLevel.ALL
                logger = object: Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }.also { Napier.base(DebugAntilog()) }
            defaultRequest {
                header("Content-Type", "application/json")
                headers.append("app_version","102")
                url(ApiUtils.BASEURL)
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 60_000
                requestTimeoutMillis = 60_000
            }

        }

    }

}
fun initKoin() {
    startKoin {
        modules(
            appModule,
            viewModelModule,
            networkModule,
            jsonModule,
            prefModule
        )
    }
}


