package com.example.sergeykuchin.spacexrockets

import com.example.sergeykuchin.spacexrockets.other.DateFormatter
import org.junit.Test

class DateFormatterTest {

    @Test
    fun formatDateFromServer() {
        val dateFromServer = "2018-04-18T18:51:00-04:00"
        val expectedDate = "19.04.2018 03:51" //YEKT locale

        val resultDate = DateFormatter().formatFromServerFormatToUserFormat(dateFromServer)
        assert(resultDate == expectedDate)
    }
}
