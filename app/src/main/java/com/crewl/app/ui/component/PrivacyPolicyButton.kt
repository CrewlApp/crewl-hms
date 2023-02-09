package com.crewl.app.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.R
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.Shapes
import com.crewl.app.ui.theme.SubtitleColor
import com.crewl.app.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun PrivacyPolicyButton() {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    var targetOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val coroutineScope = rememberCoroutineScope()

    val updatedOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(height = 55.dp)
                .zIndex(1.0f)
                .padding(end = 5.dp)
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 2.dp, color = Black, shape = Shapes.small)
                .background(White, shape = Shapes.small)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 10.dp)
                        .size(30.dp)
                        .padding(5.dp)
                        .pointerInput(buttonState) {
                            detectTapGestures(
                                onPress = {
                                    buttonState = if (buttonState == ButtonState.Idle) {
                                        coroutineScope.launch {
                                            updatedOffset.animateTo(targetOffset)
                                        }
                                        ButtonState.Pressed
                                    } else {
                                        coroutineScope.launch {
                                            updatedOffset.animateTo(Offset(0f, 0f))
                                        }
                                        ButtonState.Idle
                                    }
                                }
                            )
                        }
                        .clip(Shapes.small)
                        .background(White, Shapes.small)
                        .border(2.dp, Black, Shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    if (buttonState == ButtonState.Pressed)
                        Image(modifier = Modifier.padding(2.dp), painter = painterResource(id = R.drawable.ic_check_24), contentDescription = null)
                }

                Text(modifier = Modifier.padding(end = 10.dp),
                    text =  buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.Bold)) {
                            append("Kullanım Koşulları ")
                        }
                        withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal)) {
                            append("ve")
                        }
                        withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.Bold)) {
                            append(" Güvenlik Sözleşmesi")
                        }
                        withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal)) {
                            append("‘ni okudum ve kabul ediyorum.")
                        }
                    },
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Start
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 55.dp)
                .zIndex(0.0f)
                .padding(start = 5.dp)
                .onGloballyPositioned {
                    val rect = it.boundsInParent()
                    targetOffset = rect.topLeft
                }
                .background(Black, shape = Shapes.small)
                .align(Alignment.BottomEnd)
        ) {}
    }
}

@Preview
@Composable
fun PreviewPrivacyPolicy() {
    PrivacyPolicyButton()
}