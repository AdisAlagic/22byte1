package com.adisalagic.a22bytetest.objects

import androidx.annotation.DrawableRes

data class Book(
    val name: String,
    @DrawableRes val id: Int,
    val quote: String,
)
