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
    implementation("org.bitcoinj:bitcoinj-core:0.15.8")
    implementation("com.squareup.okhttp3:okhttp:3.14.2")

    testImplementation("junit:junit:4.13.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
}

tasks.wrapper {
    gradleVersion = "7.4"
}