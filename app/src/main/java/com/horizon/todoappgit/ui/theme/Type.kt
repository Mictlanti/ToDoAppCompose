package com.horizon.todoappgit.ui.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.horizon.todoappgit.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val abelFont = FontFamily(
    Font(
        googleFont = GoogleFont("Abel"),
        fontProvider = provider
    )
)

val robotoMono = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto Mono"),
        fontProvider = provider
    )
)