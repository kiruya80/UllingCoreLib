# UllingCoreLib

- Android Module을 JitPack을 이용하여 배포하기 참고 사이트
http://thdev.tech/androiddev/2016/09/14/Android-AAR-Publish-Jit.html

- Github 라이브러리 jitpack 추가하기
https://jitpack.io/

- Library 
root - build.gradle 추가 
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.0'
        // 추가하기
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5' 
    }
}

Library - build.gradle 추가 
// 추가하기
apply plugin: 'com.android.library'
apply plugin: 'android-maven'

// com.github.github ID
group='com.github.kiruya80'

android {
    compileSdkVersion 24
    buildToolsVersion "24.0.2"

    defaultConfig {
//        applicationId "com.ulling.lib"
        minSdkVersion 16
        targetSdkVersion 24
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
        }
    }
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.google.code.gson:gson:2.2.4'
}

- JitPack을 배포 https://jitpack.io/

- app
root - build.gradle 추가
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.0'
        classpath 'com.google.gms:google-services:3.0.0' 
    }
}

allprojects {
    repositories {
        jcenter()
        // 추가하기
        maven { url "https://jitpack.io" }
    }
}

app - build.gradle 추가 
apply plugin: 'com.android.application' 

android {
    compileSdkVersion 24
    buildToolsVersion "24.0.2"
    defaultConfig {
        applicationId "com.ulling.firebasetest"
        minSdkVersion 16
        targetSdkVersion 24
        versionCode 1
        versionName "1.0"
        multiDexEnabled true
    }
    buildTypes {
        release {
            minifyEnabled true 
        }
    }
    productFlavors {
    }
}

dependencies {
    compile 'com.android.support:appcompat-v7:24.2.1'
    compile 'com.android.support:design:24.2.1'
    compile 'com.google.firebase:firebase-messaging:9.6.1'
    compile 'com.google.firebase:firebase-crash:9.6.1'
    compile 'com.google.firebase:firebase-config:9.6.1'
    compile 'com.github.{username}:{repository}:{tag}'
    compile 'com.github.kiruya80:UllingCoreLib:0.0.12' 
}
// ADD THIS AT THE BOTTOM
apply plugin: 'com.google.gms.google-services'
 
