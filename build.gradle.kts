import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version:String by extra
buildscript{
    var kotlin_version:String by extra
    kotlin_version="1.3.61"
    repositories{
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies{
        classpath(kotlin("gradle-plugin",kotlin_version))
    }
}
plugins {
    kotlin("jvm") version "1.3.61"
    application
}

application{
    mainClassName="org.hakuka.game.AppKt"
}
dependencies {
    compile(kotlin("stdlib-jdk8",kotlin_version))
    compile("com.github.shaunxiao:kotlinGameEngine:v0.0.4")
}
repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}