package com.abhyudaya.githubredesign.utils

import okhttp3.internal.Util
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest{

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
        assertEquals("Updated 2 years ago", Utils().getLastUpdatedAt("2020-06-14T18:42:39Z"))
        assertEquals("Updated 1 month ago", Utils().getLastUpdatedAt("2022-01-14T18:42:39Z"))
        assertEquals("Updated 3 days ago", Utils().getLastUpdatedAt("2022-02-14T18:42:39Z"))
        assertEquals("Updated few minutes ago", Utils().getLastUpdatedAt("2022-02-17T23:42:39Z"))
    }
}