# Kotlin Camera

### Application Level
````Gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}


````
### Module Level
````Gradle
android {
  ...
  packagingOptions { 
    exclude 'META-INF/library_release.kotlin_module' 
  } 
  ...
}

dependencies {
  ...
  compile 'com.github.apg-mobile:kotlin-camera:1.0.10' 
  ...
}
````


### AndroidManifest

````XML
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  ...
  <uses-permission android:name="android.permission.CAMERA"/>
  ...
</manifest>
````
