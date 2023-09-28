package com.crewl.app.ui.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crewl.app.R
import com.crewl.app.data.model.country.Country
import com.crewl.app.framework.extension.getString
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.getCountries
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.getEmojiFromLocal
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.searchCountries
import com.crewl.app.ui.theme.Black
import com.crewl.app.ui.theme.SpaceGrotesk

object CountryCodeScreen {
    @Composable
    fun Header() {
        CountryCodeHeaderSection()
    }

    @Composable
    fun Main(
        onItemSelected: (Country) -> Unit,
        isKeyboardHidden: (Boolean) -> Unit
    ) {
        CountryCodeMainSection(
            onItemSelected = onItemSelected,
            isKeyboardHidden = isKeyboardHidden
        )
    }
}

@Composable
private fun CountryCodeHeaderSection() {
    Text(
        modifier = Modifier.padding(horizontal = 10.dp),
        text = getString(id = R.string.COUNTRY_CODE),
        fontFamily = SpaceGrotesk,
        fontSize = 24.sp,
        color = Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun CountryCodeMainSection(
    onItemSelected: (country: Country) -> Unit = {},
    isKeyboardHidden: (Boolean) -> Unit
) {
    val countries = remember { getCountries() }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        searchQuery = CountryCodeSearchView()

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            contentPadding = PaddingValues(4.dp)
        ) {
            items(
                items = if (searchQuery.isEmpty())
                    countries
                else
                    countries.searchCountries(searchQuery),
                key = { country ->
                    country.code
                }
            ) { country ->
                CountryItem(country) {
                    onItemSelected(it)
                }
            }
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun CountryCodeSearchView(): String {
    var isClearIconShown by rememberSaveable { mutableStateOf(false) }
    var searchQuery: String by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    isClearIconShown = searchQuery.isNotEmpty()

    Row {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .background(
                        Color.LightGray.copy(0.6f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                value = searchQuery,
                onValueChange = { typedQuery ->
                    searchQuery = typedQuery
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 14.sp
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black.copy(0.3f)
                    )
                },
                trailingIcon = {
                    if (isClearIconShown)
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                tint = Color.Black.copy(0.3f),
                                contentDescription = "Clear icon"
                            )
                        }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
            )
            if (searchQuery.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.phone_number),
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 52.dp)
                )
            }
        }
    }

    return searchQuery
}

@Composable
private fun CountryItem(
    country: Country,
    onItemSelected: (Country) -> Unit = {}
) {
    Row(modifier = Modifier
        .clickable {
            onItemSelected(country)
        }
        .padding(horizontal = 4.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically)
    {
        Text(text = getEmojiFromLocal(country.code), fontSize = 22.sp)

        Text(
            text = country.name,
            modifier = Modifier
                .padding(10.dp)
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