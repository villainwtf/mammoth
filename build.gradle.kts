import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

plugins {
    id("java")
    id("maven-publish")
}

group = "wtf.villain"
version = runCommand("git", "rev-parse", "--short", "HEAD").trim()

subprojects {
    apply<JavaLibraryPlugin>()
    apply<MavenPublishPlugin>()

    repositories {
        mavenCentral()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }

        withSourcesJar()
        withJavadocJar()
    }

    tasks.withType<Javadoc> {
        val docletOptions = options as StandardJavadocDocletOptions

        docletOptions.tags(
            "apiNote:a:API Note:",
            "implSpec:a:Implementation Requirements:",
            "implNote:a:Implementation Note:"
        )
    }

    publishing {
        publications {
            create<MavenPublication>("library") {
                groupId = "${rootProject.group}.${project.group}"
                artifactId = project.name
                version = rootProject.version.toString()

                from(components["java"])
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/villainwtf/mammoth")

                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

fun runCommand(vararg args: String): String {
    val out = ByteArrayOutputStream()

    exec {
        commandLine = args.toMutableList()
        standardOutput = out
    }

    return out.toString(Charset.defaultCharset())
}
