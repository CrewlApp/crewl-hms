package com.crewl.app.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crewl.app.R
import com.crewl.app.data.mock.MockData
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.Inter
import com.crewl.app.ui.theme.LightTypography
import com.crewl.app.ui.theme.SubtitleColor

@Composable
fun ToolbarView(
    @StringRes titleId: Int,
    @StringRes descriptionId: Int = 0,
    descriptionStrings: List<String> = listOf(),
) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = titleId),
            style = LightTypography.h1.copy(
                fontSize = 24.sp,
                textAlign = TextAlign.Start
            ),
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (descriptionId != 0) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = descriptionId),
                style = MaterialTheme.typography.body1.copy(
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    color = SubtitleColor,
                    textAlign = TextAlign.Start,
                    lineHeight = 24.sp,
                    letterSpacing = (-0.25).sp
                ),
            )
        } else {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = descriptionStrings[0] + " ",
                    style = MaterialTheme.typography.body1.copy(
                        fontFamily = Inter,
                        color = Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        lineHeight = 24.sp,
                        letterSpacing = (-0.25).sp
                    ),
                )

                Text(
                    text = descriptionStrings[1] ?: descriptionStrings[0],
                    style = MaterialTheme.typography.body1.copy(
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        color = SubtitleColor,
                        textAlign = TextAlign.Start,
                        lineHeight = 24.sp,
                        letterSpacing = (-0.25).sp
                    ),
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewToolbarView() {
    ToolbarView(titleId = MockData.ToolbarView.Title, descriptionId = MockData.ToolbarView.Description)
}