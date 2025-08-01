package com.horizon.todoappgit.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.horizon.todoappgit.ui.theme.abelFont
import com.horizon.todoappgit.ui.theme.robotoMono

@Composable
fun HeadLineLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight = FontWeight.Bold,
    fontSize: TextUnit = 30.sp
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        fontFamily = abelFont
    )
}

@Composable
fun BodyLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight = FontWeight.W800,
    fontSize: TextUnit = 25.sp
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        fontFamily = robotoMono
    )
}

@Composable
fun BodyMedium(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontWeight: FontWeight = FontWeight.W400,
    fontSize: TextUnit = 15.sp
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontWeight = fontWeight,
        fontSize = fontSize,
        fontFamily = robotoMono
    )
}