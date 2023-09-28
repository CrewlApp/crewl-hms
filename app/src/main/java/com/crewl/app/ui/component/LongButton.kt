/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.ui.component

import androidx.compose.runtime.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.R
import com.crewl.app.framework.extension.gesturesDisabled
import com.crewl.app.framework.extension.getString
import com.crewl.app.ui.theme.*
import kotlinx.coroutines.launch

enum class ButtonState {
    Pressed, Idle
}

@Composable
fun LongButton(
    fraction: Float = 1f,
    foregroundColor: Color = BrightGold,
    text: String = "",
    isActive: Boolean = true,
    isLoading: Boolean = false,
    onButtonClick: () -> Unit = {}
) {
    val buttonState by remember { mutableStateOf(ButtonState.Idle) }
    var targetOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val coroutineScope = rememberCoroutineScope()

    val updatedOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .height(55.dp)
            .alpha(
                if (!isActive) 0.6f else 1f
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 50.dp)
                .zIndex(1.0f)
                .padding(end = 5.dp)
                .gesturesDisabled(!isActive)
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
                            onButtonClick.invoke()
                        }
                    )
                }
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 2.dp, color = Black, shape = RoundedCornerShape(0.dp))
                .background(foregroundColor, shape = RoundedCornerShape(0.dp))
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (!isLoading)
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = text,
                    fontFamily = SpaceGrotesk,
                    fontSize = 16.sp,
                    color = Black,
                    letterSpacing = -(0.15).sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            else
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .size(17.dp),
                    color = Black,
                    strokeWidth = 2.dp
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
                .background(Black, shape = RoundedCornerShape(0.dp))
                .align(Alignment.BottomEnd)
        ) {}
    }
}

@Preview
@Composable
fun PreviewAnimatedButton() {
    LongButton(text = getString(R.string.SEND_CODE))
}
