package com.crewl.app.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crewl.app.R
import com.crewl.app.framework.extension.getString
import com.crewl.app.helper.Constants.PRIVACY_POLICY_TAG
import com.crewl.app.helper.Constants.TERMS_OF_SERVICE_TAG
import com.crewl.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PrivacyPolicyButton(
    isKeyboardHidden: (Boolean) -> Unit = {},
    isUserChecked: (Boolean) -> Unit = {},
    onPrivacyPolicyClicked: () -> Unit = {},
    onTermsOfServiceClicked: () -> Unit = {},
) {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    var targetOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    val coroutineScope = rememberCoroutineScope()

    val updatedOffset by remember { mutableStateOf(Animatable(Offset(0f, 0f), Offset.VectorConverter)) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 55.dp)
                .zIndex(1.0f)
                .padding(end = 5.dp)
                .graphicsLayer {
                    this.translationX = updatedOffset.value.x
                    this.translationY = updatedOffset.value.y
                }
                .border(width = 1.5.dp, color = Black, shape = RoundedCornerShape(2.dp))
                .background(White, shape = RoundedCornerShape(2.dp))
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.padding(end = 10.dp), verticalAlignment = Alignment.CenterVertically) {
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
                                        isUserChecked(true)

                                        coroutineScope.launch {
                                            updatedOffset.animateTo(targetOffset)
                                        }
                                        ButtonState.Pressed
                                    } else {
                                        isUserChecked(false)

                                        coroutineScope.launch {
                                            updatedOffset.animateTo(Offset(0f, 0f))
                                        }
                                        ButtonState.Idle
                                    }
                                }
                            )
                        }
                        .clip(RoundedCornerShape(2.dp))
                        .background(White, shape = RoundedCornerShape(2.dp))
                        .border(1.5.dp, Black, RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (buttonState == ButtonState.Pressed) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(Black, RoundedCornerShape(2.dp))
                        )
                    }
                }

                val policyString = if (LocalInspectionMode.current) getFakeData() else getPolicyString()

                ClickableText(
                    text = policyString,
                    onClick = { offset ->
                        policyString.getStringAnnotations(
                            tag = TERMS_OF_SERVICE_TAG,
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            coroutineScope.launch {
                                isKeyboardHidden(true)
                                onPrivacyPolicyClicked()
                            }
                        }

                        policyString.getStringAnnotations(
                            tag = PRIVACY_POLICY_TAG,
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            coroutineScope.launch {
                                isKeyboardHidden(true)
                                onTermsOfServiceClicked()
                            }
                        }
                    },
                    style = TextStyle(
                        fontSize = 11.sp,
                        fontFamily = Inter,
                        lineHeight = 16.sp
                    )
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
                .background(SoftPeach, shape = RoundedCornerShape(2.dp))
                .border(1.5.dp, Black, shape = RoundedCornerShape(2.dp))
                .align(Alignment.BottomEnd)
        ) {}
    }
}

private fun getFakeData(): AnnotatedString {
    return buildAnnotatedString {
        pushStringAnnotation(tag = "termsOfService", annotation = "termsOfService")
        withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.SemiBold)) {
            append("Kullanım Koşulları ")
        }
        pop()
        withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal)) {
            append("ve")
        }
        pushStringAnnotation(tag = "privacyPolicy", annotation = "privacyPolicy")
        withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.SemiBold)) {
            append(" Güvenlik Sözleşmesi")
        }
        pop()
        withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal)) {
            append("‘ni okudum ve kabul ediyorum.")
        }
    }
}

private fun getPolicyString(): AnnotatedString {
    return buildAnnotatedString {
        pushStringAnnotation(tag = TERMS_OF_SERVICE_TAG, annotation = TERMS_OF_SERVICE_TAG)
        withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)) {
            append(getString(R.string.TERMS_OF_SERVICE))
        }
        pop()
        withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal, fontSize = 13.sp)) {
            append(" " + getString(R.string.AND) + " ")
        }
        pushStringAnnotation(tag = PRIVACY_POLICY_TAG, annotation = PRIVACY_POLICY_TAG)
        withStyle(style = SpanStyle(color = Black, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)) {
            append(getString(R.string.PRIVACY_POLICY))
        }
        pop()
        withStyle(style = SpanStyle(color = SubtitleColor, fontWeight = FontWeight.Normal, fontSize = 13.sp)) {
            append(getString(R.string.I_READ_AND_AGREE))
        }
    }
}

@Preview
@Composable
fun PreviewPrivacyPolicyButton() {
    PrivacyPolicyButton()
}