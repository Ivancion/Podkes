package com.example.podcastapp.domain.use_case

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetFormattedDateUseCase @Inject constructor() {
    operator fun invoke(longDate: Long, pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Date(longDate)
        return dateFormat.format(date)
    }
}