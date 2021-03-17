# UllingCoreLib

1. 배포전 사용시

 1) 신규 프로젝트 settings.gradle에서 아래 저장 경로

<pre><code>
include ':UllingCoreLib'
project(':UllingCoreLib').projectDir=new File('C:\\Android\\AndroidStudioGit\\UllingCoreLib\\UllingCoreLib')
</code></pre>
 
 2) app폴더 내 build.gradle에서 project 추가

<pre><code>
dependencies {
    implementation project(':UllingCoreLib')
    }
</code></pre>


2. 배포 이후 사용시 
- Android Module을 JitPack을 이용하여 배포하기 참고 사이트  
http://thdev.tech/androiddev/2016/09/14/Android-AAR-Publish-Jit.html  
https://thdev.tech/androiddev/2016/09/14/Android-AAR-Publish-Jit/

- Github 라이브러리 jitpack 추가하기  
https://jitpack.io/  
 
 
> Library 
----------
 
1-1. root - build.gradle 추가  

<pre><code>
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
 </code></pre>
  
1-2. Library - build.gradle 추가  
<pre><code>   
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
 </code></pre>


> JitPack 배포  
---------- 
1. https://jitpack.io/ 
2. kiruya80/UllingCoreLib 입력 Look up 라이브러리 조회
3. 라이브러리 Get it

> app  
---------- 

1. root - build.gradle 추가  
<pre><code>
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
        maven { url "https://jitpack.io" }
    }
}
</code></pre>
  
2. app - build.gradle 추가   
<pre><code>
apply plugin: 'com.android.application' 
apply plugin: 'com.google.gms.google-services'
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
    compile 'com.github.kiruya80:UllingCoreLib:0.0.12' 
} 
</code></pre>
 
