plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    `java-library`
}

repositories {
    mavenCentral()
    mavenLocal()
    jcenter()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

group = "com.adumate"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
