# Position Mock [![Build Status](https://travis-ci.org/mitchhentges/position-mock.svg?branch=master)](https://travis-ci.org/mitchhentges/position-mock)

Mocks a static location on the Android platform.
Built using Maven, because Gradle is slow, less awesome, and has less glorious IntelliJ integration

## Building

Google dropped support for getting Android sources from Maven local, so you're going to need to install Android 5.1
to "maven local" to build Position Mock.

1. Install Android SDK
2. Install Android 22 Platform and Google Play services
3. Get a [Google Maps API Key](https://developers.google.com/maps/documentation/android/signup)
4. Save your key in `~/gradle.properties` under the variable `positionMockApiKey`
3. Run `gradle build`

## Purpose

I used to use "Fake Location" for mocking my position on Android, but after you use it long enough, it bombards
you with ads. Now's a good time to make a streamlined open-source version.

Mockup of ending version:
![Mockup](http://i.imgur.com/oDQr7JN.png)