plugins {
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.bitcoinj:bitcoinj-core:0.16.1")

    implementation("commons-codec:commons-codec:1.13")
    implementation("io.ktor:ktor-client-apache:2.0.1")
    implementation("io.ktor:ktor-client-content-negotiation:2.0.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.0.1")

    testImplementation("junit:junit:4.13.1")
    testImplementation("io.kotest:kotest-assertions-core:4.6.4")
    testImplementation("io.mockk:mockk:1.12.4")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            group = "elrond"
            artifactId = "elrond-sdk-erdkotlin"
            version = "0.1.0"

            from(components["java"])
        }
    }
}

tasks.wrapper {
    gradleVersion = "7.4"
}