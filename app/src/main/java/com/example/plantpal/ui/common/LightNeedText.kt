package com.example.plantpal.ui.common

import com.example.plantpal.data.LightNeed

fun LightNeed.label(): String = when (this) {
    LightNeed.LOW -> "Low light"
    LightNeed.MEDIUM -> "Medium light"
    LightNeed.BRIGHT -> "Bright light"
}

fun LightNeed.hint(): String = when (this) {
    LightNeed.LOW -> "Shady spots, away from windows"
    LightNeed.MEDIUM -> "Near a window, no direct sun"
    LightNeed.BRIGHT -> "Sunny window or direct sun"
}