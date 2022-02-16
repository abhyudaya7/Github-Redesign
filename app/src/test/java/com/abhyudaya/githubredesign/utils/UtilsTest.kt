package com.abhyudaya.githubredesign.utils

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UtilsTest{

    @Test
    fun formattedDataTest() {
        assertEquals("100k", Utils().getFormattedData(100000))
    }

    @Test
    fun getDateTest() {
        assertEquals("14-Jun-2021", Utils().getLastUpdatedAt("2021-06-14T18:42:39Z"))
    }
}