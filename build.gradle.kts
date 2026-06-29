plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.detekt) apply false
}

subprojects {
    afterEvaluate {
        val hasKotlin = plugins.hasPlugin("org.jetbrains.kotlin.android") ||
            plugins.hasPlugin("org.jetbrains.kotlin.jvm")
        if (hasKotlin) {
            apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
            extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
                config.from(rootProject.file("detekt.yml"))
                buildUponDefaultConfig = true
                parallel = true
            }
            dependencies {
                add("detektPlugins", rootProject.libs.detekt.formatting)
            }
        }
    }
}

tasks.register("ciCheck") {
    group = "verification"
    description = "Runs the same checks as GitHub Actions CI"
    dependsOn(
        ":domain:test",
        ":core:network:testDebugUnitTest",
        ":data:testDebugUnitTest",
        ":feature:cities:testDebugUnitTest",
        ":feature:citydetail:testDebugUnitTest",
        ":app:compileDebugKotlin",
        ":app:detektMain",
        ":core:common:detekt",
        ":core:network:detektMain",
        ":core:ui:detektMain",
        ":domain:detekt",
        ":data:detektMain",
        ":feature:cities:detektMain",
        ":feature:citydetail:detektMain",
    )
}
