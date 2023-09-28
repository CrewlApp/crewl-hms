package com.crewl.app.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.crewl.app.ui.theme.White
import com.crewl.app.utils.WindowInfo
import com.crewl.app.utils.rememberWindowInfo

@ExperimentalMaterialApi
@Composable
fun CrewlSheetContent(
    header: @Composable () -> Unit,
    main: @Composable () -> Unit,
    footer: @Composable () -> Unit
) {
    val windowInfo = rememberWindowInfo()
    val size = Size(width = windowInfo.screenWidth.toFloat(), height = windowInfo.screenHeight.toFloat())

    val bottomSheetHeight =
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) (size.height * 0.9).dp else size.height.dp

    Column(
        modifier = Modifier
            .background(White)
            .height(bottomSheetHeight)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Grabber(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(10.dp))

        header()

        Spacer(modifier = Modifier.height(10.dp))

        main()

        Spacer(modifier = Modifier.height(10.dp))

        footer()
    }
}
