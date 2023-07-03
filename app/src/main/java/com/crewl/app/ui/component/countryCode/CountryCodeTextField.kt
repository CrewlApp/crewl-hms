package com.crewl.app.ui.component.countryCode

import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import com.crewl.app.data.model.country.Country
import com.crewl.app.helper.countryCode.CountryCodeHelper.Companion.getCountries
import kotlinx.coroutines.coroutineScope

@Composable
fun CountryCodeTextField(
    label: String = "",
    isError: Boolean = false,
    modifier: Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    expanded: Boolean = false,
    selectedCountry: Country? = null,
    defaultSelectedCountry: Country = getCountries().first(),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    onExpandedChange: () -> Unit,
) {
    val countryValue = "${defaultSelectedCountry.dialogCode} ${defaultSelectedCountry.name}"

    OutlinedTextField(
        modifier = modifier
            .expandable(menuLabel = label, onExpandedChange = onExpandedChange),
        readOnly = true,
        isError = isError,
        label = { Text(text = label) },
        value = if (selectedCountry == null) countryValue else "${selectedCountry.dialogCode} ${selectedCountry.name}",
        onValueChange = {},
        colors = colors,
        shape = shape,
        trailingIcon = {
            Icon(
                Icons.Filled.ArrowDropDown,
                null,
                Modifier.rotate(
                    if (expanded)
                        180f
                    else
                        0f
                )
            )
        }
    )
}

@Composable
fun Modifier.expandable(
    onExpandedChange: () -> Unit,
    menuLabel: String
) = pointerInput(Unit) {
    forEachGesture {
        coroutineScope {
            awaitPointerEventScope {
                var event: PointerEvent
                do {
                    event = awaitPointerEvent(PointerEventPass.Initial)
                } while (
                    !event.changes.all { it.changedToUp() }
                )
                onExpandedChange.invoke()
            }
        }
    }
}.semantics {
    contentDescription = menuLabel // this should be a localised string
    onClick {
        onExpandedChange()
        true
    }
}