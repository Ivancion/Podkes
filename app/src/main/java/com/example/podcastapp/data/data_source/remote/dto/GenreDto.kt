package com.example.podcastapp.data.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    val id: Int,
    val name: String,
    @SerializedName("parent_id")
    val parentId: Int
)