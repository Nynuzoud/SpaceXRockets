package com.example.sergeykuchin.spacexrockets.other

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    //2018-04-18T22:51:00.000Z
    private val serverUTCDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    private val userDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    private val yearOnlyFormat = SimpleDateFormat("yyyy", Locale.getDefault())

    fun formatFromServerFormatToUserFormat(serverDate: String): String {
        val date = serverUTCDateFormatter.parse(serverDate)
        return userDateFormat.format(date)
    }

    fun formatFromServerFormatToYearOnly(serverDate: String): String {
        val date = serverUTCDateFormatter.parse(serverDate)
        return yearOnlyFormat.format(date)
    }
}