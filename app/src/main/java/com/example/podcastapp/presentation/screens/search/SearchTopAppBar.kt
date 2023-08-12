package com.example.podcastapp.presentation.screens.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.podcastapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onSettingsClicked: () -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = 0.6f),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            trailingIcon = {
                Row {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(
                        onClick = {
                            onSettingsClicked()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.tune_icon),
                            contentDescription = "Tune Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = Color.Transparent
            )
        )
    }
}