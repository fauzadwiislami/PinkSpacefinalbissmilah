# PinkSpace : Smart Period & Ovulation Tracker

PinkSpace is a secure, cloud-synced Android application designed to help users track their menstrual cycles, predict ovulation, and monitor fertile windows with high precision. Developed using Kotlin, the application prioritizes data privacy, structural stability, and a seamless user experience for managing sensitive reproductive health information. By replacing manual calendar tracking with an automated system, PinkSpace ensures that cycle records are accurately calculated and permanently safeguarded against device loss.

## Main Features

- **User Authentication:** Users can register and log in using their email address and password.
- **Terms and Conditions:** Users must agree to the terms and conditions before creating an account.
- **Period Prediction:** The application calculates the next menstrual period based on the date of the last menstrual period and cycle length.
- **Fertile Window Estimation:** The application displays the estimated start and end of the fertile period.
- **Ovulation Date:** The application estimates the user's ovulation date.
- **Daily Log:** Users can record moods, menstrual flow, and daily notes.
- **Calendar View:** Users can select and view dates through a calendar view.
- **Profile Management:** Users can update their username, avatar, average cycle length, and average menstrual duration.
- **Logout:** Users can log out of their account and return to the login page.

## Architecture & Paradigm

This app uses an Activity- and Fragment-based Android structure. Each main feature is separated into different components to make the code more organized and easier to develop.

- **Authentication Layer:** handles user login, registration, and logout processes using Firebase Authentication.
- **Main Navigation Layer:** manages navigation through the main page through `MainActivity` and several fragments.
- **Data Processing Layer:** handles menstrual cycle calculations using the `PeriodCalculator.kt` file.
- **Database Layer:** stores user data, cycle data, and diary entries using Firebase.
- **User Interface Layer:** displays application pages such as Home, Calendar, Log, and Profile.

## Stack & Dependencies

- **Language:** Kotlin
- **Platform:** Android
- **IDE:** Android Studio
- **Build System:** Gradle Kotlin DSL
- **Authentication:** Firebase Authentication
- **Database:** Firebase Firestore and Firebase Realtime Database
- **UI Components:** AndroidX, Material Components, ConstraintLayout
- **View Binding:** used to connect XML layouts with Kotlin code more safely and efficiently.

## Project Structure

```text
PinkSpacefinalbissmilah/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/pinkspace/
│   │   │   │   ├── LoginActivity.kt
│   │   │   │   ├── RegisterActivity.kt
│   │   │   │   ├── TermsActivity.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── HomeFragment.kt
│   │   │   │   ├── CalendarFragment.kt
│   │   │   │   ├── LogFragment.kt
│   │   │   │   ├── ProfileFragment.kt
│   │   │   │   ├── SplashActivity.kt
│   │   │   │   └── PeriodCalculator.kt
│   │   │   │
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │
│   ├── build.gradle.kts
│   ├── google-services.json
│   └── proguard-rules.pro
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
└── gradlew.bat
