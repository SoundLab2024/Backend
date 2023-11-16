plugins {
	java
	id("org.springframework.boot") version "3.2.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.soundlab"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	// Server Web (Tomcat)
	implementation("org.springframework.boot:spring-boot-starter-web")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	// Postgres
	runtimeOnly("org.postgresql:postgresql")

	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// MapStruct
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

	// Lombok Annotation
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// OpenAPI docs
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	// Live reloading
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Field Validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Test support
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}