package com.crewl.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crewl.app.ui.theme.White
import com.crewl.app.utils.WindowInfo
import com.crewl.app.utils.rememberWindowInfo

@ExperimentalMaterialApi
@Composable
fun CrewlContentScreen(
    header: @Composable () -> Unit,
    main: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    val windowInfo = rememberWindowInfo()
    val screenWidth = windowInfo.screenWidth
    val screenHeight = windowInfo.screenHeight
    val bottomSheetHeight =
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) (screenHeight * 0.9).dp else screenHeight.dp

    Column(
        modifier = Modifier
            .background(White)
            .height(bottomSheetHeight)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        header()

        Spacer(modifier = Modifier.height(10.dp))

        main()

        Spacer(modifier = Modifier.height(10.dp))

        footer()
    }
}
