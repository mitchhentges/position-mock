# Position Mock

Mocks a static location on the Android platform.
Built using Maven, because Gradle is slow, less awesome, and has less glorious IntelliJ integration

## Building

Google dropped support for getting Android sources from Maven local, so you're going to need to install Android 5.1
to "maven local" to build Position Mock.

1. Install Android SDK
2. Install Android 21 Platform
3. Clone [Maven Android SDK Deployer](https://github.com/simpligility/maven-android-sdk-deployer)
4. Run `mvn install -P 5.1`
5. Run `mvn package` in this repository