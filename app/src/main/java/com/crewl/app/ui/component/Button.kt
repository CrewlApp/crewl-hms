package com.crewl.app.ui.component

import androidx.compose.runtime.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.BrightGold
import com.crewl.app.ui.theme.Shapes
import kotlinx.coroutines.launch
import timber.log.Timber

enum class ButtonState {
    Pressed, Idle
}

@Composable
fun Button(fraction: Float = 1f, padding: Dp = 0.dp) {
    val buttonState by remember { mutableStateOf(ButtonState.Idle) }

    var targetOffset = remember { Offset(0f, 0f) }

    val coroutineScope = rememberCoroutineScope()

    val updatedOffset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .height(55.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .zIndex(1.0f)
                .padding(end = 5.dp)
                .pointerInput(buttonState) {
                    detectTapGestures(
                        onPress = {
                            coroutineScope.launch {
                                updatedOffset.animateTo(targetOffset)
                            }
                            tryAwaitRelease()
                            coroutineScope.launch {
                                updatedOffset.animateTo(Offset(0f, 0f))
                            }
                        }
                    )
                }
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 2.dp, color = Black, shape = Shapes.large)
                .background(BrightGold, shape = Shapes.large)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(),
                text = "Devam Et",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .zIndex(0.0f)
                .padding(start = 5.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    targetOffset = rect.topLeft
                }
                .background(Black, shape = Shapes.large)
                .align(Alignment.BottomEnd)
        ) {}
    }
}

@Preview
@Composable
fun PreviewButton() {
    Button()
}