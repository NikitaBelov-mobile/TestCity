package com.example.testcity.core.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val TestCityLightColorScheme = lightColorScheme(
    primary = TestCityColors.Primary,
    onPrimary = Color.White,
    background = TestCityColors.Background,
    onBackground = TestCityColors.TextPrimary,
    surface = TestCityColors.Surface,
    onSurface = TestCityColors.TextPrimary,
    error = TestCityColors.Error,
    onError = Color.White,
    outline = TestCityColors.Divider,
)
