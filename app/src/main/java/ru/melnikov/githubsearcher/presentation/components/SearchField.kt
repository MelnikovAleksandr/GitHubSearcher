package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.melnikov.githubsearcher.R

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    inputText: String,
    maxInputLength: Int = 25,
    onUpdateText: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    validInputRegex: Regex = Regex(".*"),
    isTrimText: Boolean = true,
    onSearchIconCLick: () -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {

    TextField(
        value = inputText,
        onValueChange = { newValue ->
            if (newValue.length <= maxInputLength && newValue.matches(validInputRegex))
                onUpdateText(if (isTrimText) newValue.trim() else newValue)
        },
        singleLine = true,
        label = {
            if (inputText.length >= maxInputLength) {
                Text(
                    text = stringResource(id = R.string.input_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium
                )
            } else {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (inputText.isNotBlank()) {
                    onSearchIconCLick()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            }),
        trailingIcon = {
            IconButton(onClick = {
                onSearchIconCLick()
                keyboardController?.hide()
                focusManager.clearFocus()
            }, enabled = inputText.isNotBlank()) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "sent",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}