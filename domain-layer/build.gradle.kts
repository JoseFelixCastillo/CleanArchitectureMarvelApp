plugins {
    id(Plugins.javaLibrary)
    id(Plugins.kotlin)
    id(Plugins.kotlinKapt)
    id(Plugins.detekt)
}

dependencies {
    implementation(fileTree("libs") { include(listOf("*.jar", "*.aar")) })
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.kotlinCoroutinesCore)
    // 3rd party libraries
    implementation(Libraries.koinCore)
    api(Libraries.arrowCore)
    api(Libraries.arrowSyntax)
    kapt(Libraries.arrowMeta)
    detekt(Libraries.detektFormatting)
    detekt(Libraries.detektCli)
    // testing dependencies - Unit Test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoKotlin)
    testImplementation(Libraries.kotlinCoroutinesTest)
    // koin testing tools
    testImplementation(Libraries.koinTest)
}
