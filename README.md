# Droidcon Madrid
Kotlin Multiplatform is here! 🙌

I'm going to give a talk at Droidcon Madrid about it:
<br>
👉 The Hitchhikers Guide Through Kotlin Multiplaform

<br>
This repository contains a demo of an application developed to show how we can have a shared logic module between two different targets - in this case, Android and iOS.


## Use case
This is a multi-screen application that allows you to see the schedule, speakers and select all your favourite talks that are going to happen at Droidcon Madrid.

<h3 align="center">
  <img src="screens/app_screens.png" alt="App Screens" />
</h3>


## Implementation
The application communicated with Sessionize API in order to fetch the schedule and speakers data; this will also be stored on a local database.

This way, `common` contains all this logic needed and `androidMain` and `iosMain` are just responsible to ask for data to populate the views, in this case a RecyclerView and a UITableViewController accordingly.


## Libraries
- io.ktor:ktor-client-core
- io.ktor:ktor-client-serialization
- org.jetbrains.kotlinx:kotlinx-coroutines
- com.squareup.sqldelight


## How to run
1. Edit local.properties to contain the path to Android SDK
2. Open the project with IntelliJ
3. Select auto-import

Wait for project to sync.

**Compile for Android:**
1. Go to "Add configuration" and select "Android App"
2. Add a name to the configuration (for example "android")
3. Select "app" module and click "OK"

Click on Run to compile and install it on your Android device


**Compile for iOS**
1. Open the iosApp/iosApp.xcodeproj from Xcode

Click on Run to compile and install it on your iOS device

## Extra
If you want to create your first project using Kotlin Multiplatform for Android and iOS you can find this medium post that I've writen [here](https://medium.com/@cafonsomota/set-up-your-first-kotlin-multiplatform-project-for-android-and-ios-e54c2b6574e7).


## More information
We're on the beginning of Kotlin Multiplatform, and there are already amazing projects out there that it take it a bit further, don't forget to give a look at:
- [Kotlin Conf app](https://github.com/jetbrains/kotlinconf-app)
- [Touchlab Droidcon Kotlin](https://github.com/touchlab/DroidconKotlin)
- [Kotlin Academy App](https://github.com/MarcinMoskala/KotlinAcademyApp)
- [Ktor Samples](https://github.com/ktorio/ktor-samples)
- [Kotlin Multiplatform Template](https://github.com/pink-room/kotlin-multiplatform-template)
