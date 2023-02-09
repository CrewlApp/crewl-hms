package com.crewl.app.ui.feature.onboarding

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
import androidx.navigation.NavHostController
import com.crewl.app.R
import com.crewl.app.data.model.onboarding.OnboardingItem
import com.crewl.app.ui.component.AnimatedButton
import com.crewl.app.ui.router.Screen
import com.crewl.app.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navigator: NavHostController, viewModel: OnboardingViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val itemsOfOnboarding = viewModel.data.items

    val pagerState = rememberPagerState()
    val coroutinesScope = rememberCoroutineScope()

    LaunchedEffect(key1 = context) {
        viewModel.onboardingEvent.collect { event ->
            when (event) {
                is OnboardingEvent.Navigate -> {
                    navigator.navigate(route = Screen.PreHomeScreen.route)
                }
            }
        }
    }

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
            AnimatedButton(fraction = 0.75f, text = stringResource(id = R.string.continue_string)) {
                if (pagerState.currentPage + 1 < itemsOfOnboarding.size)
                    coroutinesScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                else viewModel.saveOnboardingState(isCompleted = true)
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
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            color = Black,
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
                .padding(horizontal = 25.dp),
            text = onboardingItem.description,
            fontFamily = SpaceGrotesk,
            color = SubtitleColor,
            fontSize = 16.sp,
            letterSpacing = (-0.25).sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_2_XL)
fun PreviewButton() {
    CrewlTheme {
        PagerScreen(OnboardingItem.First)
    }
}

