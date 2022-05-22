plugins {
    kotlin("jvm") version "1.6.20"
}

group = "elrond"
version = "0.4.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("commons-codec:commons-codec:1.13")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("org.bitcoinj:bitcoinj-core:0.16.1")

    implementation("io.ktor:ktor-client-apache:2.0.1")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.1")

    testImplementation("junit:junit:4.13.1")
    testImplementation("io.kotest:kotest-assertions-core:4.6.4")
    testImplementation("io.mockk:mockk:1.12.4")
}

tasks.wrapper {
    gradleVersion = "7.4"
}