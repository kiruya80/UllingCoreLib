# UllingCoreLib

- Android Module을 JitPack을 이용하여 배포하기 참고 사이트
http://thdev.tech/androiddev/2016/09/14/Android-AAR-Publish-Jit.html

- Github 라이브러리 jitpack 추가하기
https://jitpack.io/

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
    compile 'com.github.kiruya80:UllingCoreLib:0.0.10'
}
