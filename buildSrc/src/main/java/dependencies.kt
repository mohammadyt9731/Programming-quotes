object ReleaseVersion {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionOffset = 0

    const val versionCode =
        (versionMajor * 10000 + versionMinor * 100 + versionPatch) * 100 + versionOffset
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"
}

object Versions {
    //BuildTools and SDK
    const val compileSdk = 33
    const val minSdk = 21
    const val targetSdk = 33

    const val activityComposeVersion = "1.6.1"
    const val composeVersion = "1.3.2"
    const val navigationVersion = "2.5.3"
    const val hiltNavigationComposeVersion = "1.1.0-alpha01"
    const val coreKtxVersion = "1.9.0"
    const val lifecycleRtKtxVersion = "2.5.1"
    const val lifecycleExtensions = "2.2.0"
    const val lifecycle = "2.5.1"
    const val junit = "4.13.2"
    const val testExtJunit = "1.1.5"
    const val testEspresso = "3.5.1"
    const val accompanist = "0.27.0"
    const val hilt = "2.44"
    const val hiltCompiler = "2.43.2"
    const val retrofit = "2.9.0"
    const val okhttp = "4.10.0"
    const val gson = "2.9.1"
    const val gsonFactory = "2.9.0"
    const val room = "2.4.3"
}

object Libraries {

    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"

    //compose
    const val activity = "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
    const val ui = "androidx.compose.ui:ui:${Versions.composeVersion}"
    const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    const val material = "androidx.compose.material:material:${Versions.composeVersion}"
    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigationVersion}"
    const val hiltNavigation =
        "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}"

    //test compose
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Versions.composeVersion}"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}"
    const val tooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"


    //lifecycle
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRtKtxVersion}"
    const val lifecycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    //test
    const val junit = "junit:junit:${Versions.junit}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtJunit}"
    const val testEspresso = "androidx.test.espresso:espresso-core:${Versions.testEspresso}"

    //accompanist
    const val accompanistSystemUiController =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"
    const val accompanistPager = "com.google.accompanist:accompanist-pager:${Versions.accompanist}"
    const val accompanistPagerIndicators =
        "com.google.accompanist:accompanist-pager-indicators:${Versions.accompanist}"
    const val accompanistFlowLayout =
        "com.google.accompanist:accompanist-flowlayout:${Versions.accompanist}"

    //hilt
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"

    // network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttp3 = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonConvertor = "com.squareup.retrofit2:converter-gson:${Versions.gsonFactory}"

    //room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}

object AnnotationProcessors {
    const val room = "androidx.room:room-compiler:${Versions.room}"
    const val hilt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
}

object Modules {
    const val app = ":app"
    const val base = ":base"
    const val commonAndroid = ":common-android"
    const val common = ":common-ui"
    const val di = ":di"

    const val authorModel = ":feature-author:author-model"
    const val authorData = ":feature-author:author-data"
    const val authorsDomain = ":feature-author:author-domain"
    const val authorUi = ":feature-author:author-ui"

    const val quoteModel = ":feature-quote:quote-model"
    const val quoteData = ":feature-quote:quote-data"
    const val quoteDomain = ":feature-quote:quote-domain"
    const val quoteUI = ":feature-quote:quote-ui"
}