package com.example.podcastapp.presentation.screens.search.components

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.example.podcastapp.presentation.screens.search.DropdownMenuState
import com.example.podcastapp.presentation.screens.search.components.lazydropdownmenu.LazyDropdownMenu

@Composable
fun SettingsMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    genresDropdownMenuState: DropdownMenuState,
    onGenreSelected: (String) -> Unit,
    onGenresMenuExpandedChange: (Boolean) -> Unit,
    regionsDropdownMenuState: DropdownMenuState,
    onRegionSelected: (String) -> Unit,
    onRegionsMenuExpandedChange: (Boolean) -> Unit,
    languagesDropdownMenuState: DropdownMenuState,
    onLanguageSelected: (String) -> Unit,
    onLanguagesMenuExpandedChange: (Boolean) -> Unit,
    onSubmitSettings: () -> Unit
) {
    val inverseSurfaceColor = MaterialTheme.colorScheme.inverseSurface

    var menuOffset by remember {
        mutableStateOf(IntOffset.Zero)
    }

    val visibleState = remember { MutableTransitionState(false) }
    visibleState.targetState = expanded

    val transition = updateTransition(visibleState, label = "SettingsMenu")

    val scale by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 300)
        }, label = ""
    ) { visible ->
        if (visible) 1f else 0.8f
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 150)
        }, label = ""
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0f
        }
    }

    if(visibleState.currentState || visibleState.targetState) {
        Popup(
            popupPositionProvider = object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    val offset = IntOffset(
                        anchorBounds.left,
                        anchorBounds.top
                    )
                    menuOffset = offset
                    return offset
                }
            },
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            Row(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(
                        color = inverseSurfaceColor,
                        shape = MaterialTheme.shapes.medium
                    ),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    ExposedDropdownMenuList(
                        offset = menuOffset,
                        onMenuExpandedChange = onGenresMenuExpandedChange,
                        onSelectItem = onGenreSelected,
                        dropdownMenuState = genresDropdownMenuState,
                        title = "Genre"
                    )
                    ExposedDropdownMenuList(
                        offset = menuOffset,
                        onMenuExpandedChange = onRegionsMenuExpandedChange,
                        onSelectItem = onRegionSelected,
                        dropdownMenuState = regionsDropdownMenuState,
                        title = "Region"
                    )
                    ExposedDropdownMenuList(
                        offset = menuOffset,
                        onMenuExpandedChange = onLanguagesMenuExpandedChange,
                        onSelectItem = onLanguageSelected,
                        dropdownMenuState = languagesDropdownMenuState,
                        title = "Language"
                    )
                }
                ElevatedButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 16.dp, end = 16.dp),
                    onClick = {
                        onSubmitSettings()
                    }
                ) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ExposedDropdownMenuList(
    offset: IntOffset,
    onMenuExpandedChange: (Boolean) -> Unit,
    onSelectItem: (String) -> Unit,
    dropdownMenuState: DropdownMenuState,
    title: String
) {

    val popupWidth = remember {
        mutableStateOf(0)
    }
    val density = LocalDensity.current

    Column {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        BoxWithConstraints {
            popupWidth.value = constraints.maxWidth
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.inverseOnSurface,
                        MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onMenuExpandedChange(true)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    text = dropdownMenuState.selectedText,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Icon(
                    imageVector = if (dropdownMenuState.expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }

        LazyDropdownMenu(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .width(with(density) { popupWidth.value.toDp() }),
            expanded = dropdownMenuState.expanded,
            offset = DpOffset(
                with(density) { offset.x.toDp() },
                with(density) { offset.y.toDp() }
            ),
            onDismissRequest = { onMenuExpandedChange(false) },
            content = {
                items(dropdownMenuState.dropdownItemList) {
                    DropdownMenuItem(
                        text = {
                            Text(it)
                        },
                        onClick = {
                            onSelectItem(it)
                            onMenuExpandedChange(false)
                        }
                    )
                }
            }
        )
    }
}

