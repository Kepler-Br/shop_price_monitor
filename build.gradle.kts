import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

        plugins {
            id("org.springframework.boot") version "2.7.17"
            id("io.spring.dependency-management") version "1.0.15.RELEASE"
            kotlin("jvm") version "1.6.21"
            kotlin("plugin.spring") version "1.6.21"
        }

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    val seleniumVersion: String by project
    val micrometerVersion: String by project

    implementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.micrometer:micrometer-registry-atlas:$micrometerVersion")

    implementation("io.micrometer:micrometer-spring-legacy:1.3.20")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

