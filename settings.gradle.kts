rootProject.name = "mammoth"

listOf(
    "common",
    "platform-packetevents"
).forEach {
    include("mammoth-$it")
    project(":mammoth-$it").projectDir = file(it)
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("adventure", "4.14.0")
            version("packetevents", "2.2.0")

            library("adventure-api", "net.kyori", "adventure-api").versionRef("adventure")
            library("adventure-text-gson", "net.kyori", "adventure-text-serializer-gson").versionRef("adventure")
            library("adventure-text-legacy", "net.kyori", "adventure-text-serializer-legacy").versionRef("adventure")
            library("packetevents", "com.github.retrooper.packetevents", "api").versionRef("packetevents")

            bundle("adventure-serializers", listOf("adventure-text-gson", "adventure-text-legacy"))
        }
    }
}