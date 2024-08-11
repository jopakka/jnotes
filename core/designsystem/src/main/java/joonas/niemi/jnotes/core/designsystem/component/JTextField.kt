package joonas.niemi.jnotes.core.designsystem.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.designsystem.util.keyboardAsState

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun JTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    containerModifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: Boolean = false,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    placeholder: String = "",
    label: String? = null,
    secured: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onSubmit: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    inputTransformation: InputTransformation? = null,
) {
    val showPlaceholder by remember { derivedStateOf { state.text.isEmpty() } }
    val textStyle =
        MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
    val placeholderTextStyle = textStyle.copy(color = textStyle.color.copy(alpha = 0.5f))

    val keyboardActions by rememberUpdatedState(newValue = KeyboardActions { onSubmit() })
    val interactionSource = remember { MutableInteractionSource() }

    val focusManager = LocalFocusManager.current
    val (focusRequester) = FocusRequester.createRefs()
    val keyboardState by keyboardAsState()

    LaunchedEffect(keyboardState) {
        if (!keyboardState) {
            focusManager.clearFocus()
        }
    }

    val maxLines = remember(lineLimits, secured) {
        if (secured) return@remember 1
        when (lineLimits) {
            is TextFieldLineLimits.MultiLine -> lineLimits.maxHeightInLines
            is TextFieldLineLimits.SingleLine -> 1
        }
    }

    val textFieldModifier = Modifier.focusRequester(focusRequester)

    val textFieldContainerModifier = modifier
        .defaultMinSize(minHeight = JTextFieldDefaults.minHeight)
        .clip(JTextFieldDefaults.shape)
        .background(JTextFieldDefaults.backgroundColor)
        .border(
            JTextFieldDefaults.borderWidth,
            if (error) JTextFieldDefaults.onErrorColor else JTextFieldDefaults.onBackgroundColor,
            JTextFieldDefaults.shape
        )
        .clickable(interactionSource = interactionSource, indication = null, onClick = {
            focusRequester.requestFocus()
        })
        .padding(JTextFieldDefaults.innerPadding)

    Column(
        modifier = containerModifier,
    ) {
        if (label != null) {
            InputLabel(text = label)
        }
        val decorator: @Composable (@Composable () -> Unit) -> Unit = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(JTextFieldDefaults.iconSpacing),
                modifier = textFieldContainerModifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                }
                JTextFieldDefaults.PlaceholderDecorator(
                    showPlaceholder = showPlaceholder,
                    placeholder = placeholder,
                    maxLines = maxLines,
                    placeholderTextStyle = placeholderTextStyle,
                    content = it,
                    modifier = Modifier.weight(1f),
                )
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        }

        if (secured) {
            BasicSecureTextField(
                state = state,
                enabled = enabled,
                textStyle = textStyle,
                onSubmit = {
                    onSubmit()
                    true
                },
                imeAction = keyboardOptions.imeAction,
                interactionSource = interactionSource,
                modifier = textFieldModifier,
                inputTransformation = inputTransformation,
                decorator = { decorator(it) },
            )
        } else {
            BasicTextField2(
                state = state,
                enabled = enabled,
                textStyle = textStyle,
                lineLimits = lineLimits,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                interactionSource = interactionSource,
                modifier = textFieldModifier,
                decorator = { decorator(it) },
            )
        }
    }
}

object JTextFieldDefaults {
    val shape: Shape
        @Composable get() = MaterialTheme.shapes.small

    val backgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.background

    val onBackgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground

    val onErrorColor: Color
        @Composable get() = MaterialTheme.colorScheme.error

    val innerPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
    val iconSpacing: Dp = 8.dp
    val borderWidth: Dp = 2.dp
    val minHeight: Dp = 56.dp


    @Composable
    fun PlaceholderDecorator(
        showPlaceholder: Boolean,
        placeholder: String?,
        maxLines: Int,
        placeholderTextStyle: TextStyle,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit = {},
    ) {
        Box(modifier) {
            if (placeholder != null && showPlaceholder) {
                Text(
                    text = placeholder,
                    maxLines = maxLines,
                    style = placeholderTextStyle,
                )
            }
            content()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewJTextFieldSingleLine() {
    val state = rememberTextFieldState("Hello text")

    JNotesTheme {
        JTextField(
            state = state,
            containerModifier = Modifier.padding(8.dp),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewJTextFieldMultiLine() {
    val state = rememberTextFieldState("Hello multi-\nline text.\nMore list\n to be added")

    JNotesTheme {
        JTextField(
            state = state,
            containerModifier = Modifier.padding(8.dp),
            lineLimits = TextFieldLineLimits.MultiLine(1, 3),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewJTextFieldPlaceholder() {
    val state = rememberTextFieldState("")

    JNotesTheme {
        JTextField(
            state = state,
            containerModifier = Modifier.padding(8.dp),
            placeholder = "Placeholder",
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewJTextFieldLabel() {
    val state = rememberTextFieldState("We have label")

    JNotesTheme {
        JTextField(
            state = state,
            containerModifier = Modifier.padding(8.dp),
            placeholder = "Placeholder",
            label = "Label",
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewJTextFieldSecured() {
    val state = rememberTextFieldState("This is password")

    JNotesTheme {
        JTextField(
            state = state,
            containerModifier = Modifier.padding(8.dp),
            placeholder = "Placeholder",
            label = "Label",
            secured = true,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewJTextFieldIcons() {
    val state = rememberTextFieldState("This is trailing icon")

    JNotesTheme {
        JTextField(
            state = state,
            modifier = Modifier.fillMaxWidth(),
            containerModifier = Modifier.padding(8.dp),
            label = "Label",
            trailingIcon = {
                Icon(Icons.Default.Search, contentDescription = null)
            },
            leadingIcon = {
                Icon(Icons.Default.Star, contentDescription = null)
            },
        )
    }
}
