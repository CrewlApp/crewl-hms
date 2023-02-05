package com.crewl.app.ui.feature.prehome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crewl.app.R
import com.crewl.app.ui.component.TextButton
import com.crewl.app.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun PreHomeScreen(viewModel: PreHomeViewModel = hiltViewModel()) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(0.4f),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(0.4f),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            TextButton(fraction = 0.80f, foregroundColor = White, text = stringResource(id = R.string.login_string))

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(fraction = 0.80f, foregroundColor = BrightGold, text = stringResource(id = R.string.register_string))
        }
    }
}

@Composable
fun MainSection() {

}

@Composable
fun ButtonSection() {
}

@Preview
@Composable
fun PreviewPreHome() {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(350.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            TextButton(fraction = 0.75f, foregroundColor = White)

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(fraction = 0.75f, foregroundColor = BrightGold)
        }
    }
}
