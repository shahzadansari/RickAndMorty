package com.example.components

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL, name = "Light")
@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, name = "Dark")
@Preview(apiLevel = 33, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE, name = "Green")
@Preview(apiLevel = 33, wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE, name = "Red")
@Preview(apiLevel = 33, wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE, name = "Yellow")
@Preview(apiLevel = 33, wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE, name = "Blue")
annotation class Previews