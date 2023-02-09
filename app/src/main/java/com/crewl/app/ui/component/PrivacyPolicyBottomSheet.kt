package com.crewl.app.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crewl.app.R
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.SpaceGrotesk
import com.crewl.app.ui.theme.SubtitleColor
import com.crewl.app.ui.theme.White
import com.crewl.app.utils.WindowInfo
import com.crewl.app.utils.rememberWindowInfo

@ExperimentalMaterialApi
@Composable
fun PrivacyPolicyBottomSheet(
    painter: Painter,
    text: String
) {
    val windowInfo = rememberWindowInfo()
    val screenWidth = windowInfo.screenWidth
    val screenHeight = windowInfo.screenHeight
    val bottomSheetHeight =
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) (screenHeight * 0.9).dp else screenHeight.dp

    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(White)
            .height(bottomSheetHeight)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Image(
            modifier = Modifier.padding(horizontal = 50.dp),
            painter = painter,
            contentDescription = text
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = text,
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            color = Black,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        val scroll = rememberScrollState(0)

        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 15.dp)
                .verticalScroll(scroll),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n" +
                    "\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n" +
                    "\n",
            style = MaterialTheme.typography.body1.copy(
                color = SubtitleColor,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp,
                letterSpacing = (-0.05).sp
            ),
        )


    }
}
