plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.17.20"
    id("maven-publish")
}

val minecraftVersion = stonecutter.current.project
val loaderVersion = property("loader_version") as String
val yarnMappings = property("yarn_mappings") as String
val yarnGroup = if (hasProperty("yarn_group")) property("yarn_group") as String else "net.fabricmc"
val javaVersion = (property("java_version") as String).toInt()

repositories {
    maven("https://maven.legacyfabric.net/")
}

version = property("mod_version")!!
group = property("maven_group")!!

base {
    archivesName.set("anarchymod-mc-${minecraftVersion}")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("$yarnGroup:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("com.google.code.gson:gson:2.14.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

loom {
    runConfigs.all {
        ideConfigGenerated(true)
        runDir = "../../run"
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", minecraftVersion)
    inputs.property("loader_version", loaderVersion)
    inputs.property("java_version", javaVersion)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to minecraftVersion,
            "loader_version" to loaderVersion,
            "java_version" to javaVersion
        )
    }

    filesMatching("anarchymod.mixins.json") {
        expand(
            "java_version" to javaVersion
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    withSourcesJar()
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }
}
