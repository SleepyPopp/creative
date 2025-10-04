plugins {
    id("creative.java-conventions")
    `maven-publish`
    signing
}

val repositoryName: String by project
val snapshotRepository: String by project
val releaseRepository: String by project

publishing {
    repositories {
        maven {
            val snapshot = project.version.toString().endsWith("-SNAPSHOT")

            name = repositoryName
            url = if (snapshot) {
                uri(snapshotRepository)
            } else {
                uri(releaseRepository)
            }
            credentials(PasswordCredentials::class)
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("${project.group}:${project.name}")
                description.set(project.description)
                url.set("https://github.com/unnamed/creative")
                packaging = "jar"
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/license/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("yusshu")
                        name.set("Andre Roldan")
                        email.set("yusshu@unnamed.team")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/unnamed/creative.git")
                    developerConnection.set("scm:git:ssh://github.com:unnamed/creative.git")
                    url.set("https://github.com/unnamed/creative")
                }
            }
        }
    }
}

signing {
    val isJitpack = System.getenv("JITPACK") != null
    if (!isJitpack) {
        sign(publishing.publications["maven"])
    }
}