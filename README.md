# kotlin-camera

### Application Level
````
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}

````
### Module Level
````
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
