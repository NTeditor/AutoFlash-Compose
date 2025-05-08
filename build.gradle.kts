import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.1.20"
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.github.nteditor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation("io.github.vinceglb:filekit-dialogs:0.10.0-beta03")
    implementation("io.github.vinceglb:filekit-dialogs-compose:0.10.0-beta03")
    implementation("io.github.vinceglb:filekit-core:0.10.0-beta03")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            linux {
                modules("jdk.security.auth")
            }

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "autoflash-compose"
            packageVersion = "1.0.0"
        }
    }
}
