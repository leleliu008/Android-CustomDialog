buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        //Android Gradle插件
        //https://developer.android.com/studio/build/gradle-plugin-3-0-0-migration.html
        classpath("com.android.tools.build:gradle:3.1.2")

        //Kotlin编译的插件
        //http://kotlinlang.org/docs/reference/using-gradle.html
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.41")
    }
}

allprojects {
    repositories {
        jcenter()
        google()
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}
