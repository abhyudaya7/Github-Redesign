package com.abhyudaya.githubredesign.utils

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

@RunWith(JUnit4::class)
class UtilsTest{

    private fun getPastTime(minutesToMinus: Long): String {
        val sampleDate =
            LocalDateTime.now().atOffset(ZoneOffset.UTC).toInstant().minus(minutesToMinus, ChronoUnit.MINUTES)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withLocale(
            Locale.getDefault()
        ).withZone(ZoneId.from(ZoneOffset.UTC))
        return formatter.format(sampleDate)
    }

    @Test
    fun formattedDataTest() {
        assertEquals("100k", Utils().getFormattedData(100000))
        assertEquals("234", Utils().getFormattedData(234))
        assertEquals("1m", Utils().getFormattedData(1000000))
        assertEquals("23k", Utils().getFormattedData(23500))
        assertEquals("1m", Utils().getFormattedData(1530020))
    }

    @Test
    fun getDateTest() {
        assertEquals("Updated few minutes ago", Utils().getLastUpdatedAt(getPastTime(10)))
        assertEquals("Updated 1 month ago", Utils().getLastUpdatedAt(getPastTime(43800)))
        assertEquals("Updated 3 days ago", Utils().getLastUpdatedAt(getPastTime(4320)))
        assertEquals("Updated 1 year ago", Utils().getLastUpdatedAt(getPastTime(525960)))
    }

    @Test
    fun getUserIdTest() {
        assertEquals("vipinhelloindia", Utils().getUserIdFromUrl("https://api.github.com/repos/vipinhelloindia/ActionBarSherlock"))
        assertEquals("greenrobot", Utils().getUserIdFromUrl("https://api.github.com/repos/greenrobot/ActionBarSherlock"))
    }
}