plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.5.11"
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "fr.codinbox"
version = "1.0.0-SNAPSHOT"
description = "A Minecraft plugin"

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
  paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")

  implementation("com.fasterxml.jackson.core:jackson-core:2.17.0")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
}

tasks {
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()

    options.release.set(17)
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
  processResources {
    filteringCharset = Charsets.UTF_8.name()
    val props = mapOf(
      "name" to project.name,
      "version" to project.version,
      "description" to project.description,
      "apiVersion" to "1.20"
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
      expand(props)
    }
  }
}
