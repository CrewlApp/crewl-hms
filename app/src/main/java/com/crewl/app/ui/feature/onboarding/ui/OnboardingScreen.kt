package com.crewl.app.ui.feature.onboarding.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crewl.app.R
import com.crewl.app.data.model.onboarding.OnboardingItem
import com.crewl.app.ui.component.TextButton
import com.crewl.app.ui.feature.destinations.PreHomeScreenDestination
import com.crewl.app.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    val context = LocalContext.current

    val itemsOfOnboarding = viewModel.itemsOfOnboarding

    val pagerState = rememberPagerState()
    val coroutinesScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = itemsOfOnboarding.count(),
            state = pagerState,
            modifier = Modifier
                .weight(1.0f)
                .fillMaxWidth()
        ) { position ->
            PagerScreen(onboardingItem = itemsOfOnboarding[position])
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth()
        ) {
            TextButton(fraction = 0.75f, text = stringResource(id = R.string.continue_string)) {
                if (pagerState.currentPage + 1 < itemsOfOnboarding.size)
                    coroutinesScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                else {
                    viewModel.saveOnboardingState(isCompleted = true)
                    navigator.navigate(PreHomeScreenDestination())
                }
            }
        }
    }
}

@Composable
fun PagerScreen(onboardingItem: OnboardingItem) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .padding(horizontal = 10.dp),
            text = onboardingItem.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            modifier = Modifier
                .width(350.dp),
            painter = painterResource(id = onboardingItem.image),
            contentDescription = "Pager Image"
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            text = onboardingItem.description,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, device = Devices.PIXEL_2_XL)
fun PreviewButton() {
    CrewlTheme {
        PagerScreen(OnboardingItem.First)
    }
}

