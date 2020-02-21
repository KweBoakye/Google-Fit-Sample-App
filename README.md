# Google-Fit-Sample-App

## Goal

  create an app that uses the Google Fit Api to display daily step count over a period of 7 days 
  
## Application WorkFlow

* Use the [RecordingClient class](https://developers.google.com/android/reference/com/google/android/gms/fitness/RecordingClient) to Record the users step count
* Use the [HistoryClient](https://developers.google.com/android/reference/com/google/android/gms/fitness/HistoryClient) to save the step count data and retrieve it for display
* Use A library such as [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) or possibly [MotionLayout](https://medium.com/androiddevelopers/working-with-dynamic-data-in-motionlayout-9dbbcfe5ff75)

## Current Status

* Manifest Permission for Activity Recognition for Api level 28 and below added
* Implementation of Runtime permision request flow for [Activity Recognition for Android Q(Api level 29)](https://developer.android.com/about/versions/10/privacy/changes#physical-activity-recognition) started
* Began Setup of [Dagger Modules](app/src/main/java/com/kweku/googlefitsampleapp/di) and Dependency injection
* Began creation of [Api Client classes](/app/src/main/java/com/kweku/googlefitsampleapp/googlefitapi)
* Application registered with [Oauth](https://developers.google.com/fit/android/get-api-key)

## To Do

* Finish Dagger Dependency Injection Graph
* Create Api Classes for the RecordingClient and HistoryClient and implement all the neccesary functions needed
* Finsish Authorisation Flow
* Implement presentation of Step Data

