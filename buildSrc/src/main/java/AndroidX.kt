object AndroidX {

    private const val coreKtxVersion = "1.10.1"
    val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

    private const val appCompatVersion = "1.6.1"
    val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"

    private const val lifecycleVmKtxVersion = "2.6.1"
    val lifecycleVmKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVmKtxVersion"
    val lifecycleVmCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVmKtxVersion"

    val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVmKtxVersion"
    val lifecycleRuntimeCompose = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVmKtxVersion"
}