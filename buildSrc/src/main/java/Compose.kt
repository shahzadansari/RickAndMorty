object Compose {

    private const val activityComposeVersion = "1.7.2"
    val activity = "androidx.activity:activity-compose:$activityComposeVersion"

    /** BOM to library version mapping: https://developer.android.com/jetpack/compose/bom/bom-mapping **/
    private const val composeBomVersion = "2023.05.01"
    val composeBom = "androidx.compose:compose-bom:$composeBomVersion"

    val ui = "androidx.compose.ui:ui"
    val util = "androidx.compose.ui:ui-util"
    val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
    val material3 = "androidx.compose.material3:material3"
}