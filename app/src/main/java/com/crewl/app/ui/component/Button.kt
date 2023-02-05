package com.crewl.app.ui.component

import androidx.compose.runtime.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.R
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.BrightGold
import com.crewl.app.ui.theme.Shapes
import com.crewl.app.ui.theme.White
import kotlinx.coroutines.launch

enum class ButtonState {
    Pressed, Idle
}

@Composable
fun TextButton(
    fraction: Float = 1f,
    padding: Dp = 0.dp,
    foregroundColor: Color = BrightGold,
    text: String = "",
    onButtonClick: () -> Unit = {}
) {
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
                            onButtonClick()
                        }
                    )
                }
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 2.dp, color = Black, shape = Shapes.large)
                .background(foregroundColor, shape = Shapes.large)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
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

@Composable
fun IconButton(
    fraction: Float = 1f,
    padding: Dp = 0.dp,
    foregroundColor: Color = White,
    onButtonClick: () -> Unit = {}
) {
    val buttonState by remember { mutableStateOf(ButtonState.Idle) }

    var targetOffset = remember { Offset(0f, 0f) }

    val coroutineScope = rememberCoroutineScope()

    val updatedOffset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .height(45.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 40.dp)
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
                            onButtonClick()
                        }
                    )
                }
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 2.dp, color = Black, shape = Shapes.large)
                .background(foregroundColor, shape = Shapes.large)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_arrow_left_24),
                alignment = Alignment.Center,
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 40.dp)
                .zIndex(0.0f)
                .padding(start = 5.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    targetOffset = rect.topLeft
                }
                .background(Black, shape = Shapes.large)
                .align(Alignment.BottomEnd)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_arrow_left_24),
                alignment = Alignment.Center,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewButton() {
    IconButton()
}