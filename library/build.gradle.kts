import com.fpliu.gradle.bintrayUploadExtension

buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        //对android-maven-gradle-plugin和gradle-bintray-plugin两个插件的包装、简化插件
        //https://github.com/leleliu008/BintrayUploadGradlePlugin
        classpath("com.fpliu:BintrayUploadGradlePlugin:1.0.0")
    }
}

apply {
    plugin("com.fpliu.bintray")
}

plugins {
    id("com.android.library")

    //用于构建jar和maven包
    //https://github.com/dcendents/android-maven-gradle-plugin
    id("com.github.dcendents.android-maven").version("2.0")

    //用于上传maven包到jCenter中
    //https://github.com/bintray/gradle-bintray-plugin
    id("com.jfrog.bintray").version("1.7.3")
}

android {
    compileSdkVersion(27)

    defaultConfig {
        minSdkVersion(11)
        targetSdkVersion(25)
        versionCode = 2
        versionName = "1.0.1"
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDir("src/main/libs")
            aidl.srcDirs("src/main/java")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    lintOptions {
        isAbortOnError = false
    }
    compileOptions {
        //使用JAVA8语法解析
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }
}

// 这里是groupId，必须填写,一般填你唯一的包名
group = "com.fpliu"

//这个是版本号，必须填写
version = android.defaultConfig.versionName ?: "1.0.0"

val rootProjectName: String = project.rootProject.name

bintrayUploadExtension {
    developerName = "leleliu008"
    developerEmail = "leleliu008@gamil.com"

    projectSiteUrl = "https://github.com/$developerName/$rootProjectName"
    projectGitUrl = "https://github.com/$developerName/$rootProjectName"

    bintrayUserName = "fpliu"
    bintrayOrganizationName = "fpliu"
    bintrayRepositoryName = "newton"
    bintrayApiKey = "xxx"
}
