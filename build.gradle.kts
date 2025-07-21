plugins {
    alias(libs.plugins.moddev)
}

val id = project.property("mod_id") as String
group = project.property("maven_group") as String
version = project.property("mod_version") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

neoForge {
    version = libs.versions.neoforge.asProvider().get()
    parchment {
        mappingsVersion = libs.versions.parchment.get()
        minecraftVersion = libs.versions.minecraft.asProvider().get()
    }
    validateAccessTransformers = true

    runs {
        configureEach {
            systemProperty("forge.logging.console.level", "debug")

            dependencies {
                additionalRuntimeClasspathConfiguration(libs.org.apache.commons.math3)
                additionalRuntimeClasspathConfiguration(libs.com.github.figuraMC.luaj.core)
                additionalRuntimeClasspathConfiguration(libs.com.github.figuraMC.luaj.jse)
                additionalRuntimeClasspathConfiguration(libs.org.apache.bcel)
            }
        }

        create("client") {
            client()
            gameDirectory = file("run/client_a")
        }

        create("client2") {
            client()
            gameDirectory = file("run/client_b")
            programArguments = listOf("--username", "mayday_memory")
        }

        create("server") {
            server()
            gameDirectory = file("run/server")
        }

        create("data") {
            data()
        }
    }

    mods {
        create(id) {
            sourceSet(sourceSets["main"])
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.shedaniel.me")
    maven("https://maven.kosmx.dev")
    maven("https://maven.blamejared.com")
    maven {
        name = "Latvian Mods"
        url = uri("https://maven.latvian.dev/")
        content {
            includeGroup("dev.latvian.mods")
        }
    }
    maven("https://maven.mrcrayfish.com/repository/maven-public/")
    maven {
        url = uri("https://maven.architectury.dev")
        content {
            includeGroup("dev.architectury")
        }
    }
    // CurseForge Maven for other mods
    maven {
        name = "CurseMaven"
        url = uri("https://cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven {
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        content {
            includeGroupByRegex("software\\.bernie.*")
            includeGroup("com.eliotlash.mclib")
        }
    }
    exclusiveContent {
        forRepository {
            maven {
                name = "Modrinth"
                url = uri("https://api.modrinth.com/maven")
            }
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
    flatDir {
        dir("libs")
    }
}

// Custom configuration for local runtime dependencies
val localRuntime by configurations.creating
configurations.runtimeClasspath.get().extendsFrom(localRuntime)

dependencies {
    implementation(libs.org.apache.commons.math3)
    jarJar(libs.org.apache.commons.math3)
    implementation(libs.com.github.figuraMC.luaj.core)
    jarJar(libs.com.github.figuraMC.luaj.core)
    implementation(libs.com.github.figuraMC.luaj.jse)
    jarJar(libs.com.github.figuraMC.luaj.jse)
    implementation(libs.org.apache.bcel)
    jarJar(libs.org.apache.bcel)

    // GeckoLib - Biblioteca de animação
    implementation(libs.software.bernie.geckolib.neoforge)

    // Cloth Config API
    compileOnly(libs.me.shedaniel.cloth.config.neoforge)
    
    // Player Animator
    compileOnly(libs.dev.kosmx.player.animation.lib.forge)
    
    // JEI Integration
    compileOnly(libs.mezz.jei.common.api)
    compileOnly(libs.mezz.jei.neoforge.api)
    runtimeOnly(libs.mezz.jei.neoforge)
    
    // Controllable - Para suporte a controles/joysticks
    compileOnly(libs.curse.controllable)
    localRuntime(libs.curse.controllable)
    
    // Framework - Dependência para o Controllable
    compileOnly(libs.curse.framework)
    localRuntime(libs.curse.framework)
    
    // KubeJS - Para integração de scripts (opcional) - usando CurseForge
    compileOnly("curse.maven:kubejs-238086:${project.property("kubejs_file_id")}")
    localRuntime("curse.maven:kubejs-238086:${project.property("kubejs_file_id")}")
    
    // Rhino - Dependência do KubeJS para execução de scripts JavaScript
    compileOnly("curse.maven:rhino-416294:6184623")
    localRuntime("curse.maven:rhino-416294:6184623")
    
    // Shoulder Surfing Reloaded - Compatibilidade de camera (opcional)
    compileOnly("curse.maven:shoulder-surfing-reloaded-243190:${project.property("shoulder_surfing_file_id")}")
    localRuntime("curse.maven:shoulder-surfing-reloaded-243190:${project.property("shoulder_surfing_file_id")}")
    
    // Iris - Para compatibilidade com shaders (opcional)
    compileOnly("curse.maven:irisshaders-455508:${project.property("iris_file_id")}")
    localRuntime("curse.maven:irisshaders-455508:${project.property("iris_file_id")}")
    
    // Optional mod integrations for testing
    localRuntime(libs.curse.carry.on)
    localRuntime(libs.curse.sodium)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}

tasks.processResources {
    val properties = mapOf(
        "mod_id" to id,
        "mod_version" to project.version,
        "mod_name" to project.property("mod_name") as String,
        "mod_license" to project.property("mod_license") as String,
        "mod_authors" to project.property("mod_authors") as String,
        "mod_description" to project.property("mod_description") as String,
        "minecraft_version_range" to libs.versions.minecraft.range.get(),
        "loader_version_range" to libs.versions.neoforge.range.get(),
        "neo_version_range" to libs.versions.neoforge.range.get()
    )
    filteringCharset = "UTF-8"
    inputs.properties(properties)
    filesMatching("META-INF/neoforge.mods.toml") { expand(properties) }
}