PinkSpace

PinkSpace adalah aplikasi Android berbasis Kotlin yang dirancang untuk membantu pengguna mencatat, memantau, dan memprediksi siklus menstruasi. Aplikasi ini menyediakan fitur prediksi periode menstruasi berikutnya, estimasi masa subur, pencatatan mood dan flow harian, kalender, serta pengelolaan profil pengguna.

Deskripsi Project

PinkSpace dikembangkan sebagai aplikasi mobile yang berfokus pada pencatatan siklus menstruasi secara sederhana dan mudah digunakan. Pengguna dapat membuat akun, login, menyetujui syarat penggunaan, mengisi data siklus, melihat hasil prediksi periode berikutnya, mencatat kondisi harian, serta mengelola informasi profil.

Aplikasi ini menggunakan Firebase sebagai layanan backend untuk autentikasi pengguna dan penyimpanan data.

Fitur Utama

1. Login Pengguna
Pengguna dapat masuk ke aplikasi menggunakan email dan password. Sistem login menggunakan Firebase Authentication.

2. Registrasi Akun
Pengguna baru dapat membuat akun dengan mengisi nama lengkap, email, username, dan password. Sistem juga melakukan validasi input seperti email wajib valid, username minimal 3 karakter, dan password minimal 6 karakter.

3. Persetujuan Terms and Conditions
Sebelum akun dibuat, pengguna harus menyetujui terms and conditions melalui checkbox persetujuan. Jika belum disetujui, tombol pembuatan akun tidak dapat digunakan.

4. Prediksi Siklus Menstruasi
Pengguna dapat memilih tanggal menstruasi terakhir dan memasukkan panjang siklus. Aplikasi kemudian menghitung:
- Prediksi menstruasi berikutnya
- Tanggal ovulasi
- Awal masa subur
- Akhir masa subur

5. Penyimpanan Data Siklus
Data hasil prediksi disimpan ke Firebase Firestore pada collection `cycles`.

6. Kalender
Aplikasi menyediakan tampilan kalender untuk memilih dan melihat tanggal. Kalender dibatasi dari 5 tahun sebelum hingga 5 tahun setelah tanggal saat ini.

7. Daily Log
Pengguna dapat mencatat kondisi harian, meliputi:
- Mood
- Flow menstruasi
- Catatan tambahan

Data log harian disimpan ke Firebase Firestore pada collection `daily_logs`.

8. Profil Pengguna
Pengguna dapat melihat dan memperbarui data profil, seperti:
- Username
- Avatar
- Rata-rata panjang siklus
- Rata-rata durasi menstruasi

9. Logout
Pengguna dapat keluar dari akun dan kembali ke halaman login.

Teknologi yang Digunakan

Project ini dikembangkan menggunakan:

- Kotlin
- Android Studio
- Gradle Kotlin DSL
- Firebase Authentication
- Firebase Firestore
- Firebase Realtime Database

Struktur Project

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
│   │   │
│   │   ├── androidTest/
│   │   └── test/
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
