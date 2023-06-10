import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.8.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation(kotlin("test"))

    //kotlin co routines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    //gui
    implementation("com.googlecode.lanterna:lanterna:3.1.1")

    //lint
    implementation("com.pinterest.ktlint:ktlint-core:0.49.1")

}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

// tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//     kotlinOptions {
//         jvmTarget = "17"
//     }
// }

application {
    mainClass.set("MainKt")
}