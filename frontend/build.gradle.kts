plugins {
    base
    id("org.openapi.generator") version "7.4.0"
}

openApiGenerate {
    generatorName.set("typescript-angular")
    inputSpec.set("${projectDir}/src/openapi/api-spec.yaml")
    outputDir.set("${projectDir}/src/app/api")
    additionalProperties.set(mapOf(
        "ngVersion" to "19.1.0",
        "supportsES6" to "true",
        "npmName" to "@app/api",
        "npmVersion" to "1.0.0",
        "withInterfaces" to true,
        "apiModulePrefix" to "" // Remove the Api prefix
    ))
    configOptions.set(mapOf(
        "serviceFileSuffix" to ".service",
        "modelFileSuffix" to ".model",
        "apiModulePrefix" to "" // Remove the Api prefix
    ))
}

tasks.register<Exec>("npmInstall") {
    group = "frontend"
    description = "Installs Node.js dependencies using npm"

    workingDir = projectDir
    commandLine("npm", "install")
    dependsOn("openApiGenerate")
}

tasks.register<Exec>("npmBuild") {
    group = "frontend"
    description = "Builds the Angular application for production"

    workingDir = projectDir
    commandLine("npm", "run", "build")
    dependsOn("npmInstall")
}

tasks.register<Delete>("npmClean") {
    group = "frontend"
    description = "Removes Node.js dependencies and build artifacts"

    delete("node_modules", "dist")
}

tasks.clean {
    group = "frontend"
    description = "Cleans the frontend project including npm artifacts"

    dependsOn("npmClean")
}

tasks.assemble {
    group = "frontend"
    description = "Assembles the frontend artifacts by building the Angular application"

    dependsOn("npmBuild")
}
