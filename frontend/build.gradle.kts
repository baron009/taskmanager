plugins {
    base
}

tasks.register<Exec>("npmInstall") {
    group = "frontend"
    description = "Installs Node.js dependencies using npm"

    workingDir = projectDir
    commandLine("npm", "install")
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
