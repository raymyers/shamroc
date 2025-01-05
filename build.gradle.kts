plugins {
    java
    application
}

group = "org.raymyers"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val antlr4 by configurations.creating

sourceSets {
    main {
        java.srcDir("build/generated-src/java")
    }
}

dependencies {
    implementation("org.antlr:antlr4-runtime:4.13.2")
    antlr4("org.antlr:antlr4:4.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


tasks.register<JavaExec>("generateSources") {
    val generatedSourceDir = layout.buildDirectory.file("generated-src/java").get()
    val generatedPackage = "org.raymyers.shamroc.generated"

    val generatedSourcePackageDir = generatedSourceDir.asFile.resolve(generatedPackage.replace('.', '/'))
    val grammarDir = projectDir.resolve("src/main/antlr")
    inputs.dir(grammarDir)
    outputs.dir(generatedSourceDir)

    mainClass = "org.antlr.v4.Tool"
    classpath = antlr4
    val grammarFiles = listOf("Sexpr.g4").map { grammarDir.resolve(it) }
    args(
        listOf("-o", generatedSourcePackageDir)
                + grammarFiles
                + listOf("-package", generatedPackage)
    )

}

tasks.compileJava {
    dependsOn("generateSources")
    options.compilerArgs.add("-Aproject=${project.group}/${project.name}")
}

