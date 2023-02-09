/**
 * @author Kaan Fırat
 *
 * @since 1.0
 */

package com.crewl.app.ui.component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.crewl.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrewlTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = TextColor,
        fontSize = 18.sp
    ),
    onValueChange: (TextFieldValue) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedLabelColor = FocusedLabelColor,
        unfocusedLabelColor = UnfocusedLabelColor,
        containerColor = BackgroundColor,
        placeholderColor = PlaceholderColor,
        textColor = TextColor,
        focusedIndicatorColor = FocusedIndicatorColor,
        unfocusedIndicatorColor = UnfocusedIndicatorColor,
        cursorColor = CursorColor
    )
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        textStyle = textStyle,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        colors = colors,
        supportingText = supportingText
    )
}