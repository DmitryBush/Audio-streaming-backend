plugins {
    id("java")
    id("org.springframework.boot") version "3.5.10" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bush"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.9")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot")
    implementation("com.fasterxml.uuid:java-uuid-generator:5.2.0")
    implementation("org.hibernate.orm:hibernate-core:6.6.40.Final")
}