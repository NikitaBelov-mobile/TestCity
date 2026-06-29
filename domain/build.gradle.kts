plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.androidx.paging.common)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
