package com.example.podcastapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
//    object MainFeed: BottomBarScreen(
//
//    )
//    object MainFeed: BottomBarScreen(
//        route = "main_feed",
//        title = "Main feed",
//        icon = Icons.Default.Home
//    )
//
//    object SavedPodcasts: BottomBarScreen(
//        route = "saved_podcasts",
//        title = "Saved Podcasts",
//        icon = Icons.Default.
//    )
}
