import kotlinx.serialization.json.Json.Default.configuration
import org.gradle.model.internal.core.ModelPath.path

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.mokoResources)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        // Required for moko-resources to work
        applyDefaultHierarchyTemplate()


        androidMain {
            dependencies {
                implementation(project.dependencies.platform(libs.compose.bom))
                implementation(libs.compose.ui)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.ktor.client.okhttp)
                implementation ("androidx.security:security-crypto:1.0.0")
                implementation ("org.mindrot:jbcrypt:0.4")
                implementation(project.dependencies.platform("com.google.firebase:firebase-bom:32.7.2"))
                implementation("com.google.firebase:firebase-analytics")
                implementation("com.google.firebase:firebase-crashlytics")
                implementation("com.google.firebase:firebase-messaging")
                implementation ("androidx.startup:startup-runtime:1.1.1")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
            }

            // Required for moko-resources to work
            dependsOn(commonMain.get())
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation("com.squareup.okio:okio:3.8.0")
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.3.1")
            implementation("com.liftric:kvault:1.12.0")
            implementation("androidx.datastore:datastore-preferences-core:1.1.0-beta01")
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("org.jetbrains.kotlinx:atomicfu:0.17.3")
            implementation("dev.icerock.moko:permissions-compose:0.15.0")
            implementation("dev.icerock.moko:geo-compose:0.6.0")


            implementation(libs.kamel)
            implementation(libs.koin.core)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.screenmodel)
            implementation(libs.moko.resources.compose)

            implementation(libs.mvvm.compose)
            implementation(libs.mvvm.flow.compose)
            implementation(libs.mvvm.livedata.compose)
            implementation(libs.napier)
            implementation (libs.ktor.client.logging)

            implementation("com.diglol.crypto:crypto:0.2.0")
            implementation("dev.gitlive:firebase-common:1.8.1")// This line
            implementation("dev.gitlive:firebase-installations:1.11.1")
            implementation("dev.gitlive:firebase-crashlytics:1.11.1")

//            implementation (project( ":kmpFor"))

        }
    }
}

android {
    namespace = "com.rider.shipatpl"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.rider.shipatpl"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation("com.google.firebase:firebase-common-ktx:20.3.3")





    }
    kotlin {
        jvmToolchain(17)
    }

}


multiplatformResources {
    multiplatformResourcesPackage = "com.icerockdev.library"
}
