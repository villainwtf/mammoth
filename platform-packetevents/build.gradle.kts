repositories {
    maven {
        name = "codemc-releases"
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }
}

dependencies {
    implementation(project(":mammoth-common"))
    implementation(libs.bundles.adventure.serializers)
    implementation(libs.packetevents)
}

tasks.withType<Javadoc> {
    enabled = false
}