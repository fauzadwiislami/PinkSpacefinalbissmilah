# PinkSpace

PinkSpace adalah aplikasi Android berbasis Kotlin yang dirancang untuk membantu pengguna mencatat, memantau, dan memprediksi siklus menstruasi secara lebih mudah. Aplikasi ini menyediakan fitur pencatatan siklus, prediksi periode menstruasi berikutnya, pencatatan mood dan flow harian, kalender, serta pengelolaan profil pengguna.

## Overview

PinkSpace dikembangkan sebagai aplikasi mobile yang berfokus pada kesehatan menstruasi. Aplikasi ini membantu pengguna dalam memahami pola siklus menstruasi melalui pencatatan data secara digital.

Dengan menggunakan aplikasi ini, pengguna dapat memasukkan tanggal menstruasi terakhir dan panjang siklus, kemudian sistem akan menghitung prediksi menstruasi berikutnya, tanggal ovulasi, serta masa subur.

## Main Features

- **User Authentication:** pengguna dapat melakukan registrasi dan login menggunakan email serta password.
- **Terms and Conditions:** pengguna wajib menyetujui syarat dan ketentuan sebelum akun dibuat.
- **Period Prediction:** aplikasi menghitung prediksi menstruasi berikutnya berdasarkan tanggal menstruasi terakhir dan panjang siklus.
- **Fertile Window Estimation:** aplikasi menampilkan estimasi awal dan akhir masa subur.
- **Ovulation Date:** aplikasi memperkirakan tanggal ovulasi pengguna.
- **Daily Log:** pengguna dapat mencatat mood, flow menstruasi, dan catatan harian.
- **Calendar View:** pengguna dapat memilih dan melihat tanggal melalui tampilan kalender.
- **Profile Management:** pengguna dapat memperbarui username, avatar, panjang siklus rata-rata, dan durasi menstruasi rata-rata.
- **Logout:** pengguna dapat keluar dari akun dan kembali ke halaman login.

## Architecture & Paradigm

Aplikasi ini menggunakan struktur Android berbasis Activity dan Fragment. Setiap fitur utama dipisahkan ke dalam komponen yang berbeda agar kode lebih terorganisasi dan mudah dikembangkan.

- **Authentication Layer:** menangani proses login, registrasi, dan logout pengguna menggunakan Firebase Authentication.
- **Main Navigation Layer:** mengatur perpindahan halaman utama melalui `MainActivity` dan beberapa fragment.
- **Data Processing Layer:** menangani perhitungan siklus menstruasi melalui file `PeriodCalculator.kt`.
- **Database Layer:** menyimpan data pengguna, data siklus, dan catatan harian menggunakan Firebase.
- **User Interface Layer:** menampilkan halaman aplikasi seperti Home, Calendar, Log, dan Profile.

## Stack & Dependencies

- **Language:** Kotlin
- **Platform:** Android
- **IDE:** Android Studio
- **Build System:** Gradle Kotlin DSL
- **Authentication:** Firebase Authentication
- **Database:** Firebase Firestore dan Firebase Realtime Database
- **UI Components:** AndroidX, Material Components, ConstraintLayout
- **View Binding:** digunakan untuk menghubungkan layout XML dengan kode Kotlin secara lebih aman dan efisien.

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
