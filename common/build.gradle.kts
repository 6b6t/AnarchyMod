plugins {
    id("java-library")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.13.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}