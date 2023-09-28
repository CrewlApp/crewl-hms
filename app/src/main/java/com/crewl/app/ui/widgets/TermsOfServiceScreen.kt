package com.crewl.app.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crewl.app.R
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.SpaceGrotesk
import com.crewl.app.ui.theme.SubtitleColor

object TermsOfServiceScreen {
    @Composable
    fun Header() {
        TermsOfServiceHeaderSection()
    }

    @Composable
    fun Main() {
        TermsOfServiceMainSection()
    }
}

@Composable
private fun TermsOfServiceHeaderSection() {
    Image(
        modifier = Modifier.padding(horizontal = 50.dp),
        painter = painterResource(id = R.drawable.img_terms_of_service),
        contentDescription = stringResource(id = R.string.terms_of_service)
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        modifier = Modifier.padding(horizontal = 10.dp),
        text = stringResource(id = R.string.terms_of_service),
        fontFamily = SpaceGrotesk,
        fontSize = 24.sp,
        color = Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun TermsOfServiceMainSection() {
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
