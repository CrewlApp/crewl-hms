package com.crewl.app.ui.component.countryCode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.crewl.app.data.model.country.Country
import com.crewl.app.ui.feature.login.countryCode.getCountries
import com.crewl.app.ui.feature.login.countryCode.getEmojiFromLocale
import com.crewl.app.ui.feature.login.countryCode.searchCountries

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountryCodeBottomSheet(
    title: @Composable () -> Unit,
    show: Boolean,
    onItemSelected: (country: Country) -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val countries = remember { getCountries(context) }
    var selectedCountry by remember { mutableStateOf(countries[0]) }
    var searchValue by remember { mutableStateOf("") }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    LaunchedEffect(key1 = show) {
        if (show) modalBottomSheetState.show()
        else modalBottomSheetState.hide()
    }

    LaunchedEffect(key1 = modalBottomSheetState.currentValue) {
        if (modalBottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
            onDismissRequest()
        }
    }
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetContent = {
            title()

            Column {
                searchValue = CountryCodeSearchView()

                LazyColumn(
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(
                        if (searchValue.isEmpty()) {
                            countries
                        } else {
                            countries.searchCountries(searchValue)
                        }
                    ) { country ->
                        Row(modifier = Modifier
                            .clickable {
                                selectedCountry = country
                                onItemSelected(selectedCountry)
                            }
                            .padding(12.dp)) {
                            Text(text = getEmojiFromLocale(country.code))
                            Text(
                                text = country.name,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(2f)
                            )
                            Text(
                                text = country.dialogCode,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                            )
                        }
                        Divider(
                            color = Color.LightGray, thickness = 0.5.dp
                        )
                    }
                }
            }

        }
    ) {
        content()
    }
}