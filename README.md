# UllingCoreLib
init

buildscript {
    ...
}

allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" } // 이 주소를 추가합니다.
    }
}

android {
    ...
}

dependencies {
    compile 'com.github.{username}:{repository}:{tag}'
}
