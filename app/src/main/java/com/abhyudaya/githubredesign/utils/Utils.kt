package com.abhyudaya.githubredesign.utils

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
        // Output format: 14-Jun-2021
        val formattedDate = date.subSequence(0, date.indexOf('T'))
        val dateList = formattedDate.split('-')
        val month: String = when(dateList[1].toInt()) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
        return "${dateList[2]}-${month}-${dateList[0]}"
    }
}