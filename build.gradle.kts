import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.openapi.generator") version "5.4.0"

	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	kotlin("plugin.jpa") version "1.6.10"
}

group = "com.test.docs"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("io.swagger.parser.v3:swagger-parser:2.0.31")
	implementation("org.openapitools:jackson-databind-nullable:0.2.2")

	implementation("org.springdoc:springdoc-openapi-data-rest:1.6.6")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.6")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

val spec = "$projectDir/src/main/resources/api-spec.yaml"
val generatedSourcesDir = "$buildDir/generated/openapi"

openApiGenerate {
	generatorName.set("kotlin-spring")
	inputSpec.set(spec)
	outputDir.set(generatedSourcesDir)
	apiPackage.set("com.test.docs.docsdemo.api")
	invokerPackage.set("com.test.docs.docsdemo.api")
	modelPackage.set("com.test.docs.docsdemo.api.model")

	configOptions.set(mapOf(
		"dateLibrary" to "java8",
		"interfaceOnly" to "true"
	))
}

sourceSets {
	getByName("main") {
		java {
			srcDir("$generatedSourcesDir/src/main/kotlin")
		}
	}
}

tasks {
	val openApiGenerate by getting

	val compileKotlin by getting {
		dependsOn(openApiGenerate)
	}
}

