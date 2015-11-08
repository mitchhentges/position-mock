# Position Mock [![Build Status](https://travis-ci.org/mitchhentges/position-mock.svg?branch=master)](https://travis-ci.org/mitchhentges/position-mock)

Mocks a static location on the Android platform.
Built using Maven, because Gradle is slow, less awesome, and has less glorious IntelliJ integration

## Building

Google dropped support for getting Android sources from Maven local, so you're going to need to install Android 5.1
to "maven local" to build Position Mock.

1. Install Android SDK
2. Run `android`, and install:
    * Android 21 Platform
    * Google Play services
    * Google Repository
    * Android Support Repository
3. Get a [Google Maps API Key](https://developers.google.com/maps/documentation/android/signup)
4. In `~/gradle.properties`, enter `positionMockApiKey=YOUR_KEY_HERE`
3. Run `gradle build`

## Purpose

I used to use "Fake Location" for mocking my position on Android, but after you use it long enough, it bombards
you with ads. Now's a good time to make a streamlined open-source version.

Screenshot:
![Screenshot](https://cloud.githubusercontent.com/assets/7784737/9149247/f44c1490-3d55-11e5-8d66-83131b235ee8.png)