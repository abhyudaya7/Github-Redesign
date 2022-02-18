package com.abhyudaya.githubredesign.utils

import java.util.*

class Utils {

    fun getFormattedData(num: Int): String {
        if (num < 1000)
            return num.toString()

        else if (num in 1000..999999)
            return "${num/1000}k"

        else
            return "${num/1000000}m"
    }

    fun getLastUpdatedAt(date: String): String {
        // Input format: 2021-06-14T18:42:39Z
        val formattedDate = date.subSequence(0, date.indexOf('T'))
        val dateList = formattedDate.split('-')
        val formattedTime = date.subSequence(date.indexOf('T')+1, date.indexOf('Z'))
        val timeList = formattedTime.split(':')
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hr = c.get(Calendar.HOUR_OF_DAY)
        var interval: Int

        if (year != dateList[0].toInt()){
            interval = year - dateList[0].toInt()
            if (interval == 1)
                return "Updated $interval year ago"
            return "Updated $interval years ago"
        }

        if (month+1 != dateList[1].toInt()){
            interval = month+1 - dateList[1].toInt()
            if (interval == 1)
                return "Updated $interval month ago"
            return "Updated $interval months ago"
        }

        if (day != dateList[2].toInt()) {
            interval = day - dateList[2].toInt()
            if (interval == 1)
                return "Updated $interval day ago"
            return "Updated $interval days ago"
        }

        if (hr != timeList[0].toInt()) {
            interval = hr - timeList[0].toInt()
            if (interval == 1)
                return "Updated $interval hour ago"
            return "Updated $interval hours ago"
        }

        return "Updated few minutes ago"
    }

    fun getUserIdFromUrl(url: String): String {
        val tempUrl = url.subSequence(0, url.lastIndexOf('/'))
        val userId = tempUrl.subSequence(tempUrl.lastIndexOf('/')+1, tempUrl.length)
        return userId.toString()
    }
}