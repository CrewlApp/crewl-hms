package com.crewl.app.ui.component

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
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
import com.crewl.app.framework.extension.gesturesDisabled
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.BrightGold
import com.crewl.app.ui.theme.Shapes
import com.crewl.app.ui.theme.SpaceGrotesk
import com.crewl.app.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun BackButton(
    isActive: Boolean = true,
    onBackPressed: () -> Unit = {}
) {
    val buttonState by remember { mutableStateOf(ButtonState.Idle) }
    var targetOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val coroutineScope = rememberCoroutineScope()

    val updatedOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(33.dp)
            .alpha(
                if (!isActive) 0.6f else 1f
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(30.dp)
                .zIndex(1.0f)
                .padding(end = 3.dp)
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
                                withContext(Dispatchers.Main) {
                                    onBackPressed.invoke()
                                }
                            }
                        }
                    )
                }
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 1.5.dp, color = Black, shape = RoundedCornerShape(0.dp))
                .background(White, shape = RoundedCornerShape(0.dp))
                .padding(5.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(30.dp)
                .zIndex(0.0f)
                .padding(start = 3.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    targetOffset = rect.topLeft
                }
                .background(Black, shape = RoundedCornerShape(0.dp))
                .padding(5.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewBackButton() {
    BackButton()
}
