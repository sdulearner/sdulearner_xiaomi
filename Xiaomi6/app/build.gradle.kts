plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.xiaomi6"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.xiaomi6"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // todo: 本地引用CircleImageView
//    implementation(":CircleImageView")
    implementation("com.example.sdulearner_library:image:1.0.0")
    //引用Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //引用EventBus
    implementation("org.greenrobot:eventbus:3.3.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("io.github.cymchad:BaseRecyclerViewAdapterHelper:3.0.14")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

}