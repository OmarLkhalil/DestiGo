package com.mobilebreakero.destigo.ui.theme

import androidx.compose.ui.graphics.Color

val Blue80 = Color(0xFF4F80FF)
val Grey80 = Color(0xFFB0B0B0)

val tertiary80 = Color(
    (Blue80.red + Grey80.red) / 2,
    (Blue80.green + Grey80.green) / 2,
    (Blue80.blue + Grey80.blue) / 2,
    (Blue80.alpha + Grey80.alpha) / 2
)

val Blue40 = Color(0xFFD5E1FF)
val BlueGrey40 = Color(0xFFEDF1FD)

val tertiary40 = Color(
    (Blue40.red + BlueGrey40.red) / 2,
    (Blue40.green + BlueGrey40.green) / 2,
    (Blue40.blue + BlueGrey40.blue) / 2,
    (Blue40.alpha + BlueGrey40.alpha) / 2
)