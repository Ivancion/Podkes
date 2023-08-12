package com.example.podcastapp.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.podcastapp.presentation.theme.PodcastAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    hint: String,
    text: String,
    onTextChanged: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onTextChanged,
        placeholder = {
            Text(hint)
        },
        shape = RoundedCornerShape(16.dp),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    PodcastAppTheme(
        darkTheme = false
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SearchTextField(
                hint = "Search",
                text = "",
                onTextChanged = {}
            )
        }
    }
}