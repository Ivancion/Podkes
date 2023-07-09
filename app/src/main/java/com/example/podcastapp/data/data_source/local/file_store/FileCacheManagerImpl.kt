package com.example.podcastapp.data.data_source.local.file_store

import android.content.Context
import android.util.Log
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileReader
import java.io.FileWriter

abstract class FileCacheManagerImpl <T>(
    private val context: Context,
    private val jsonConverter: JsonConverter
): FileCacheManager<T> {

    override fun writeData(data: T) {
        val file = File(context.filesDir, fileName)
        FileWriter(file, false).use { writer ->
            val genresStr = jsonConverter.toJson(data)
            writer.write(genresStr)
        }
    }

    override fun readData(typeToken: TypeToken<T>): T {
        val file = File(context.filesDir, fileName)
        return FileReader(file).use { reader ->
            val fileContent = reader.readText()
            Log.d("FileDataManager", fileContent)
            jsonConverter.fromJson(fileContent, typeToken)
        }
    }

    override fun isFileExists(): Boolean {
        return File(context.filesDir, fileName).exists()
    }

    abstract val fileName: String

}