plugins {
    id(Plugins.androidApplication)

    id(Plugins.kotlinAndroid)
    // add lint feature
    id(Plugins.detekt)
    // add crash analytics feature
    id(Plugins.fbCrashlytics)
}

android {
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.parador.app"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        multiDexEnabled = true
    }
    buildTypes {
        named("release").configure {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    sourceSets {
        getByName("main") { java.srcDir("src/main/java") }
    }
    lintOptions {
        isAbortOnError = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(fileTree("libs") { include(listOf("*.jar", "*.aar")) })
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutinesCore)
    implementation(Libraries.kotlinCoroutinesAndroid)
    implementation(Libraries.multidex)
    implementation(Libraries.appCompat)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.material)
    implementation(Libraries.fragmentKtx)
    implementation(Libraries.lifecycle)
    implementation(Libraries.viewModelKtx)
    // other modules
/*    implementation(project(":data-layer"))
    implementation(project(":domain-layer"))
    implementation(project(":presentation-layer"))*/
    // 3rd party libraries
    implementation(Libraries.koinAndroid)
    detekt(Libraries.detektFormatting)
    detekt(Libraries.detektCli)
    debugImplementation(Libraries.leakCanary)
}
