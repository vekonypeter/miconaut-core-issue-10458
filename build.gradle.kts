plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.22"
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.3.1"
    id("io.micronaut.aot") version "4.3.1"
}

version = "0.1"
group = "com.example"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("org.mock-server:mockserver-netty:5.14.0")
}


application {
    mainClass.set("com.example.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}


graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("com.example.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}



