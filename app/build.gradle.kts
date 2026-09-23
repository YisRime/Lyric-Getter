@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    kotlin("plugin.parcelize")
}

val localProperties = Properties()
if (rootProject.file("local.properties").canRead())
    localProperties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "de.yisrime.lrcget"
    compileSdk = 36
    val buildTime = System.currentTimeMillis()
    defaultConfig {
        applicationId = "de.yisrime.lrcget"
        minSdk = 29
        targetSdk = 36
        versionCode = 103
        versionName = "1.0.3"
        dependenciesInfo.includeInApk = false
        ndk.abiFilters += arrayOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
        buildConfigField("long", "BUILD_TIME", "$buildTime")
        buildConfigField("int", "API_VERSION", "6")
        buildConfigField("int", "CONFIG_VERSION", "1")
        buildConfigField("int", "APP_RULES_API_VERSION", "14")
    }
    val config = localProperties.getProperty("androidStoreFile")?.let {
        signingConfigs.create("config") {
            storeFile = file(it)
            storePassword = localProperties.getProperty("androidStorePassword")
            keyAlias = localProperties.getProperty("androidKeyAlias")
            keyPassword = localProperties.getProperty("androidKeyPassword")
            enableV3Signing = true
            enableV4Signing = true
        }
    }
    buildTypes {
        all {
            signingConfig = config ?: signingConfigs["debug"]
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            vcsInfo.include = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
        }
    }
    packaging {
        resources {
            excludes += "**"
            merges += "META-INF/xposed/*"
        }
        dex {
            useLegacyPackaging = true
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kotlin.jvmToolchain(21)
    applicationVariants.all {
        outputs.all {
            (this as BaseVariantOutputImpl).outputFileName =
                "Lyrics Getter-$versionName-$versionCode-$name-$buildTime.apk"
        }
    }
}


configurations.all {
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-android-extensions-runtime")
}

dependencies {
    compileOnly(libs.libxposed.api)
    compileOnly(libs.dev.rikka.hidden.stub)

    implementation(libs.ezxhelper.core)
    implementation(libs.ezxhelper.xposed)
    implementation(libs.ezxhelper.android.utils)
    implementation(libs.dexkit)
    implementation(libs.dev.rikka.hidden.compat)

    implementation(libs.lyricGetter.api)
    implementation(libs.xkt)
    implementation(libs.libxposed.service)

    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.preference.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.swiperefreshlayout)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.markwon)
    implementation(libs.markwon.image)
    implementation(libs.markwon.image.glide)
}
