package com.example.podcastapp.presentation.screens.search

data class DropdownMenuState(
    val expanded: Boolean = false,
    val selectedText: String = "",
    val dropdownItemList: List<String> = emptyList()
)
