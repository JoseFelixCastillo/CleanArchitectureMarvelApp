const val kotlinVersion = "1.5.20"

object Build {
    object Versions {
        const val gradle = "4.2.2"
        const val detekt = "1.17.1"
        const val fbCrashlyticsGradle = "2.7.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    const val fbCrashlyticsGradlePlugin =
        "com.google.firebase:firebase-crashlytics-gradle:${Versions.fbCrashlyticsGradle}"
}

object Plugins {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlin = "kotlin"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val javaLibrary = "java-library"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val fbCrashlytics = "com.google.firebase.crashlytics"
    const val version = "version.gradle.kts"
}

object AndroidSdk {
    const val min = 21
    const val compile = 30
    const val target = compile
}

// librarias
object Libraries {
    // kotlin
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlinCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val kotlinCoroutinesPlayServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
    const val arrowCore = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val arrowSyntax = "io.arrow-kt:arrow-syntax:${Versions.arrow}"
    const val arrowMeta = "io.arrow-kt:arrow-meta:${Versions.arrow}"

    // androidx
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val material = "com.google.android.material:material:${Versions.googleMaterial}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"

    // leakCanary
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

    // google
    const val googleServicesAuth =
        "com.google.android.gms:play-services-auth:${Versions.googleServicesAuth}"

    // koin
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"

    // retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"

    // moshi
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    // detekt
    const val detektFormatting =
        "io.gitlab.arturbosch.detekt:detekt-formatting:${Build.Versions.detekt}"
    const val detektCli = "io.gitlab.arturbosch.detekt:detekt-cli:${Build.Versions.detekt}"

    // testing
    const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val junit = "junit:junit:${Versions.junit}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val kotlinCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val koinTest = "io.insert-koin:koin-test:${Versions.koin}"
    const val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockitoAndroid}"
    const val testRunner = "androidx.test:runner:${Versions.androidxTestRunner}"

    private object Versions {
        // core & kotlin
        const val coroutines = "1.5.1"

        // androidx
        const val multidex = "2.0.1"
        const val appCompat = "1.2.0"
        const val lifecycle = "2.3.1"
        const val constraintLayout = "2.0.4"
        const val fragmentKtx = "1.3.5"

        // 3rd party
        const val leakCanary = "2.2"
        const val googleMaterial = "1.4.0"
        const val googleServicesAuth = "19.0.0"
        const val koin = "3.1.2"
        const val arrow = "0.12.1"
        const val retrofit = "2.9.0"
        const val coroutinesAdapter = "0.9.2"
        const val moshi = "1.9.3"

        // test
        const val junit = "4.13"
        const val mockitoAndroid = "3.2.4"
        const val mockitoKotlin = "2.1.0"
        const val androidxTestRunner = "1.3.0"
    }
}
