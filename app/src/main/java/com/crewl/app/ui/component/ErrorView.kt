package com.crewl.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.SpaceGrotesk
import com.crewl.app.ui.theme.White

@Composable
fun ErrorView() {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 10.dp)
            .zIndex(20f),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .zIndex(1.0f)
                .border(width = 1.5.dp, color = Black, shape = RoundedCornerShape(0.dp))
                .background(White, shape = RoundedCornerShape(0.dp))
                .padding(10.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = "Hatalı kod girdiniz.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 12.sp,
                    color = Black,
                    letterSpacing = -(0.15).sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

            Spacer(modifier = Modifier.width(10.dp))

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp),
                    color = Black,
                )
            }
        }
    }

@Composable
@Preview
fun PreviewErrorView() {
    ErrorView()
}