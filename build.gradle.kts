plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	id("com.avast.gradle.docker-compose") version "0.14.2"
}

group = "io.tms"
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
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dockerCompose {
	isRequiredBy(tasks.named("test"))
	useComposeFiles = listOf("docker-compose.yml")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("myComposeUp") {
	dependsOn("dockerComposeUp")
}

tasks.register("myComposeDown") {
	dependsOn("dockerComposeDown")
}
