package com.crewl.app.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.BrightGold
import com.crewl.app.ui.theme.SoftPeach
import com.crewl.app.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun RegisterProgressBar(currentPageIndex: Int) {
    val totalPageSize = 5
    val pageIndex by remember { mutableStateOf(currentPageIndex) }

    val animationSpec = tween<Float>(durationMillis = 1000)

    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SoftPeach)
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val rotation by animateFloatAsState(targetValue = if (pageIndex >= i) 45f else 0f)

            val background by remember {
                mutableStateOf(
                    if (pageIndex >= i) BrightGold else White
                )
            }

            Box(
                modifier = Modifier
                    .size(15.dp)
                    .rotate(rotation)
                    .border(width = 1.5.dp, color = Black, shape = RoundedCornerShape(0.dp))
                    .background(background, shape = RoundedCornerShape(0.dp))
                    .zIndex(2f)
            )

            if (i < 5) {
                Spacer(
                    modifier = Modifier
                        .width(50.dp)
                        .height(1.5.dp)
                        .background(Color.Black)
                        .zIndex(0f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterProgressBarPreview() {
    RegisterProgressBar(3)
}