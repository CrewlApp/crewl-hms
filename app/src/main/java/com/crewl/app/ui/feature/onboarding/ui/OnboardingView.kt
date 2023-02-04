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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.alis.onboarding.ui.OnboardingViewModel
import com.crewl.app.ui.component.Button
import com.crewl.app.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val itemsOfOnboarding = listOf(
        OnboardingItem.First,
        OnboardingItem.Second,
        OnboardingItem.Third,
        OnboardingItem.Fourth
    )

    val pagerState = rememberPagerState()
    val coroutinesScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = itemsOfOnboarding.count(),
            state = pagerState,
            modifier = Modifier.weight(1.0f).fillMaxWidth()
        ) { position ->
            PagerScreen(onboardingItem = itemsOfOnboarding[position])
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(0.15f).fillMaxWidth()
        ) {
            Button(fraction = 0.75f)
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

@Composable
fun ButtonSection() {

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true, device = Devices.PIXEL_2_XL)
fun PreviewButton() {
    CrewlTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                pageCount = 2,
                modifier = Modifier.weight(1f).fillMaxWidth()
            ) { position ->
                PagerScreen(onboardingItem = OnboardingItem.First)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.weight(0.15f).fillMaxWidth()
            ) {
                Button(fraction = 0.9f)
            }
        }
    }
}

