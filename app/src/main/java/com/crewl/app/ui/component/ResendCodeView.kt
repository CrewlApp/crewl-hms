package com.crewl.app.ui.component

import android.os.CountDownTimer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crewl.app.R
import com.crewl.app.helper.Constants.RESEND_CODE_COUNTDOWN_INTERVAL
import com.crewl.app.helper.Constants.RESEND_CODE_TOTAL_MILLISECONDS
import com.crewl.app.ui.theme.*

@Composable
fun ResendCodeView(
    onResendCodeClicked: () -> Unit
) {
    var countdown: Long by remember {
        mutableStateOf(10)
    }
    
    var countdownViewReference: String by remember {
        mutableStateOf("")
    }

    val timer = object : CountDownTimer(RESEND_CODE_TOTAL_MILLISECONDS, RESEND_CODE_COUNTDOWN_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
            countdown = millisUntilFinished / RESEND_CODE_COUNTDOWN_INTERVAL
            countdownViewReference = "$countdown"
        }

        override fun onFinish() {
            countdownViewReference = "0"
        }
    }

    LaunchedEffect(key1 = Unit) {
        timer.start()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    onResendCodeClicked()
                },
            text = stringResource(id = R.string.RESEND_CODE),
            fontFamily = Inter,
            fontSize = 14.sp,
            color = if (countdown == 0L) DragonsScale else RiverLady,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            letterSpacing = -(0.20).sp
        )

        Spacer(Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(30.dp), onDraw = {
                drawCircle(color = if (countdown != 0L) DragonsScale else FourLeafCloverGreens)
            })

            Text(
                text = countdownViewReference,
                fontFamily = Inter,
                fontSize = 13.sp,
                color = if (countdown != 0L) BrightGold else White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = -(0.25).sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_2_XL)
fun PreviewResendCodeView() {
    CrewlTheme {
        ResendCodeView {}
    }
}
