plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.1.2"

tasks.register("chiseledBuild") {
    group = "project"
    dependsOn(stonecutter.tasks.named("build"))
}
