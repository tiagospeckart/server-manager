plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	id("com.avast.gradle.docker-compose") version "0.17.4"
}

group = "io.tms"
version = "0.0.2-SNAPSHOT"

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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.1.2")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
	compileOnly("org.projectlombok:lombok:1.18.26")
	developmentOnly("org.springframework.boot:spring-boot-devtools:3.0.4")
	runtimeOnly("org.postgresql:postgresql:42.5.4")
	annotationProcessor("org.projectlombok:lombok:1.18.26")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dockerCompose {
	isRequiredBy(tasks.named("test"))
	useComposeFiles = listOf("docker-compose.yml")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("updateDockerCompose") {
	doLast {
		val dockerComposeFile = file("docker-compose.yml")
		val content = dockerComposeFile.readText()
		val updatedContent = content.replace("__TAG__", project.version.toString())
		dockerComposeFile.writeText(updatedContent)
	}
}

tasks.register("dockerBuild") {
	dependsOn("updateDockerCompose") // Certifica-se de que a tarefa updateDockerCompose seja executada primeiro
	doLast {
		exec {
			commandLine("docker", "build", "--build-arg", "TAG=${project.version}", "-t", "server-manager-app:${project.version}", ".")
		}
	}
}

tasks.register("myComposeUp") {
	dependsOn("dockerBuild", "dockerComposeUp") // Certifica-se de que a tarefa dockerBuild seja executada antes de dockerComposeUp
}

tasks.register("myComposeDown") {
	dependsOn("dockerComposeDown")
}
