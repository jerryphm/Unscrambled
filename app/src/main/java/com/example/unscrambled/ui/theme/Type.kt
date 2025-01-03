package com.example.unscrambled.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.unscrambled.R

val BalooRegular = FontFamily(
    Font(resId = R.font.baloo_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displaySmall = Typography().displaySmall.copy(
        fontFamily = BalooRegular,
    ),
    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = BalooRegular
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = BalooRegular
    )
)