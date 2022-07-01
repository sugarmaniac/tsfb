package com.sugarmaniac.timeSeries.composeui

import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyAppTextFieldColors(
    textColor: Color = Color.White,
    disabledTextColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    cursorColor: Color = Color.White,
    errorCursorColor: Color = Color.White
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
)



val BOOL_TYPE = 2
val NUMBER_TYPE = 1
val TEXT_TYPE = 0

val CHANNEL_ID = "ID"
val CHANNEL_NAME = "NAME"
val NOTIFICATION_ID = 0