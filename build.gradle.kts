plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.mostlynobody"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Dependencies
    implementation(kotlin("stdlib"))

    // Test Dependencies
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}