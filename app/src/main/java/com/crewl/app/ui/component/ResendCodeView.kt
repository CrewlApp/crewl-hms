package com.crewl.app.ui.component

import android.os.CountDownTimer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.R
import com.crewl.app.framework.extension.gesturesDisabled
import com.crewl.app.framework.extension.getString
import com.crewl.app.helper.Constants.RESEND_CODE_COUNTDOWN_INTERVAL
import com.crewl.app.helper.Constants.RESEND_CODE_TOTAL_MILLISECONDS
import com.crewl.app.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ResendCodeView(onResendCodeClicked: () -> Unit) {
    var countdown: Long by remember {
        mutableStateOf(10)
    }

    var isActive: Boolean by remember {
        mutableStateOf(false)
    }

    val buttonState by remember { mutableStateOf(ButtonState.Idle) }

    var countdownViewReference: String by remember {
        mutableStateOf("")
    }

    var targetOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    val coroutineScope = rememberCoroutineScope()

    val timer = object : CountDownTimer(RESEND_CODE_TOTAL_MILLISECONDS, RESEND_CODE_COUNTDOWN_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            countdown = millisUntilFinished / RESEND_CODE_COUNTDOWN_INTERVAL
            countdownViewReference = "($countdown)"
        }

        override fun onFinish() {
            countdownViewReference = ""
            isActive = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        timer.start()
    }

    val updatedOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(45.dp)
            .alpha(
                if (!isActive) 0.6f else 1f
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(height = 40.dp)
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

                                withContext(Dispatchers.Main) {
                                    timer.start()
                                    isActive = false
                                    onResendCodeClicked.invoke()
                                }
                            }
                        }
                    )
                }
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 1.5.dp, color = Black, shape = RoundedCornerShape(2.dp))
                .background(White, shape = RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
                Text(
                    modifier = Modifier
                        .wrapContentWidth(),
                    text = "${getString(R.string.RESEND_CODE)} $countdownViewReference",
                    fontFamily = SpaceGrotesk,
                    fontSize = 14.sp,
                    color = Black,
                    letterSpacing = -(0.15).sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(height = 40.dp)
                .zIndex(0.0f)
                .padding(start = 5.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    targetOffset = rect.topLeft
                }
                .background(SoftPeach, shape = RoundedCornerShape(2.dp))
                .border(1.5.dp, Black, shape = RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp)
                .align(Alignment.BottomEnd)
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "${getString(R.string.RESEND_CODE)} $countdownViewReference",
                fontFamily = SpaceGrotesk,
                fontSize = 14.sp,
                color = Black,
                letterSpacing = -(0.15).sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun PreviewResendCodeView() {
    CrewlTheme {
        ResendCodeView {}
    }
}
