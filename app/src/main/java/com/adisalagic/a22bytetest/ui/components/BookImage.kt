package com.adisalagic.a22bytetest.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ir.kaaveh.sdpcompose.sdp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookImage(
    @DrawableRes id: Int,
    isCorrect: Boolean,
    onClick: (Boolean) -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Card(colors = CardDefaults.cardColors(containerColor = Color.Transparent), modifier = Modifier.height(120.sdp), onClick = { onClick(isCorrect) }) {
            Image(painter = painterResource(id = id), contentDescription = null)
        }
    }
}