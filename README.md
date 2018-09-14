# BVRITBusTracker

This project includes 2 android apps

> first app is [BVRIT Bus Tracker](https://github.com/fayaz07/BVRITBusTracker), this app is for students, faculties...etc(those who need to track the college buses)

> second app is [BVRIT Bus Assistant](https://github.com/fayaz07/BVRITBusAssistant) for bus drivers, they need to install this app in their mobiles so that they can give the college students, faculties their location so that they can track their bus


The basic terminology of this app is, it works on mobile [GPS](https://en.wikipedia.org/wiki/Global_Positioning_System) and [Firebase Database](https://firebase.google.com/products/realtime-database/), (which has dynamic updating capability automatically built-in), bus drivers install the [BVRIT Bus Assistant](https://github.com/fayaz07/BVRITBusAssistant) app in their mobile and they will be verified with an OTP authenticatin to their registered mobile number, after authentication once they start the tracking service on, their locaiton is grabbed using GPS(GPS and internet connection must be active) and is stored in the firebase database as a Loaction data-model. Students and others user [BVRIT Bus Tracker](https://github.com/fayaz07/BVRITBusTracker) app to track the bus in a Google Map

---
##### Let's get started with the project


Gradle section
The extra gradle libraries I have used in this particular app are(you must connect your app to firebase before implementing these libraries)

```gradle
    //For mobile authentication using OTP 
    implementation 'com.google.firebase:firebase-auth:11.8.0'
  
    //For storing data in Firebase Database 
    implementation 'com.google.firebase:firebase-database:11.8.0'
    
    //For maps API 
    implementation 'com.google.android.gms:play-services-maps:11.8.0'
    
    //Additional library for using Firebase features
    implementation 'com.google.firebase:firebase-core:11.8.0'
```

You can find the [build.gradle](https://github.com/fayaz07/bvritbustracker/blob/master/BVRITBusTracker/app/build.gradle)(module:app) file [here](https://github.com/fayaz07/bvritbustracker/blob/master/BVRITBusTracker/app/build.gradle)


### [Android Manifest section](https://github.com/fayaz07/bvritbustracker/blob/master/BVRITBusTracker/app/src/main/AndroidManifest.xml)
you can get the manifest file for BVRIT Bus Tracker [here](https://github.com/fayaz07/bvritbustracker/blob/master/BVRITBusTracker/app/src/main/AndroidManifest.xml),

The permissions required for this app are
    
    To access GPS and get location
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

* meta-data 

   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="@string/google_maps_key" />
  
  - In order to use Google Maps API, you must generate an API key 
    Read this guide, if you are a beginner for [API keys](https://developers.google.com/maps/documentation/android-sdk/intro)
   
* Activities

  - As ususal a Main activity as a launcher
