package com.example.podcastapp.data.data_source.local.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.podcastapp.data.json_converter.JsonConverter
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class DataConverter(
    private val jsonConverter: JsonConverter
) {

    @TypeConverter
    fun fromGenreIds(list: List<Int>): String {
        return jsonConverter.toJson(list)
    }

    @TypeConverter
    fun toGenreIds(data: String): List<Int> {
        return jsonConverter.fromJson(data, object: TypeToken<List<Int>>() {})
    }
}