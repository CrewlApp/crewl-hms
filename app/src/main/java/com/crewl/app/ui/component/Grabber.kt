package com.crewl.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun Grabber(modifier: Modifier) {
    Column(
        modifier = modifier
            .height(4.dp)
            .width(45.dp)
            .wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFD6D6D6))
        )
    }
}