plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.electronicstoremobileapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.electronicstoremobileapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

    dataBinding{
        enable = true;
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation("com.auth0.android:jwtdecode:2.0.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.picasso)
    // https://mvnrepository.com/artifact/com.github.mvallim/java-fluent-validator
    implementation("com.github.mvallim:java-fluent-validator:1.9.1")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation ("com.google.code.gson:gson:2.8.8")

    implementation ("androidx.fragment:fragment-ktx:1.2.2")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.activity:activity-ktx:1.1.0")
}