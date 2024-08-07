// This is the build configuration file for the Spring Security application using Gradle and Kotlin DSL.

plugins {
	java
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.Stoicodex"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

// Configuration for compile-only dependencies
configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	// Servlet API dependency
	compileOnly("javax.servlet:javax.servlet-api:4.0.1")

	// Swagger dependencies for API documentation
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	// MySQL connector for database connectivity
	implementation("mysql:mysql-connector-java:8.0.33")

	// Spring Boot starter dependencies
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Thymeleaf extras for Spring Security integration
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.0.RELEASE")

	// Jakarta Servlet API dependency
	implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")

	// Twilio SDK for sending SMS
	implementation("com.twilio.sdk:twilio:10.4.1")

	// Lombok for reducing boilerplate code
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// JSON Web Token (JWT) dependencies
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
}

tasks.withType<Test> {
	useJUnitPlatform()
}