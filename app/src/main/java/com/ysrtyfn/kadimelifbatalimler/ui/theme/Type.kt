package com.ysrtyfn.kadimelifbatalimler.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ysrtyfn.kadimelifbatalimler.R

val NotoNastaliqUrduFamily = FontFamily(
//    Font(R.font.NotoNastaliqUrdu, FontWeight.Light),
    Font(R.font.noto_tastaliq_urdu_regular, FontWeight.Normal),
//    Font(R.font.noto_tastaliq_urdu_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.noto_tastaliq_urdu_medium, FontWeight.Medium),
    Font(R.font.noto_tastaliq_urdu_bold, FontWeight.Bold)
)

val ScheherazadeNewFamily = FontFamily(
    Font(R.font.scheherazade_new_semi_bold, FontWeight.Normal),
    Font(R.font.scheherazade_new_medium, FontWeight.Medium),
    Font(R.font.scheherazade_new_semi_bold, FontWeight.SemiBold),
    Font(R.font.scheherazade_new_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Typography.kadimElifbaTakdim: TextStyle
    get() = TextStyle(
        fontFamily = ScheherazadeNewFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

val Typography.kadimElifbaTus: TextStyle
    get() = TextStyle(
        fontFamily = ScheherazadeNewFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

val Typography.kadimElifbaTusKucuk: TextStyle
    get() = TextStyle(
        fontFamily = ScheherazadeNewFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )

val Typography.kadimElifbaCevap: TextStyle
    get() = TextStyle(
        fontFamily = ScheherazadeNewFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    )

val Typography.kadimElifbaCevapKucuk: TextStyle
    get() = TextStyle(
        fontFamily = ScheherazadeNewFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    )

