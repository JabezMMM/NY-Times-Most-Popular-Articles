package me.jabez.news.app.util

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    companion object {
        fun getTime(timestamp: String): String {
            val timePublished = getFormattedDate(timestamp)
            val timeNow = getFormattedDate(Date())
            return getDifference(timeNow, timePublished)
        }

        fun getFormattedDate(string: String): Date {
            return try {
                getDateFormat(1).parse(string)
            } catch (e: java.text.ParseException) {
                try {
                    getDateFormat(2).parse(string)
                } catch (e: java.text.ParseException) {
                    getDateFormat(3).parse(string)
                }
            }
        }

        private fun getFormattedDate(date: Date): Date {
            return try {
                getDateFormat(1).parse(getDateFormat(1).format(date))
            } catch (e: java.text.ParseException) {
                try {
                    getDateFormat(2).parse(getDateFormat(2).format(date))
                } catch (e: java.text.ParseException) {
                    getDateFormat(3).parse(getDateFormat(3).format(date))
                }
            }
        }

        fun getDifference(currentTime: Date, newTime: Date): String {
            val remainingSeconds = (currentTime.time - newTime.time) / (1000)
            val remainingMinutes = (currentTime.time - newTime.time) / (1000 * 60)
            val remainingHours = (currentTime.time - newTime.time) / (1000 * 60 * 60)
            val remainingDays = (currentTime.time - newTime.time) / (1000 * 60 * 60 * 24)

            return when {
                remainingSeconds <= 120 -> "Moments ago"
                remainingMinutes <= 59 -> "$remainingMinutes minutes ago"
                remainingHours == 1L -> "$remainingHours hour ago"
                remainingHours <= 23 -> "$remainingHours hours ago"
                remainingDays == 1L -> "$remainingDays day ago"
                else -> "$remainingDays days ago"
            }
        }

        private fun getDateFormat(index: Int): SimpleDateFormat {
            val tz = TimeZone.getTimeZone("UTC")
            val dateFormat = SimpleDateFormat(
                    when (index) {
                        1 -> pattern1
                        2 -> pattern2
                        else -> pattern3
                    }
                    , Locale.US)
            dateFormat.timeZone = tz
            return dateFormat
        }

        private const val pattern1 = "yyyy-MM-dd"
        //private const val pattern1 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        private const val pattern2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val pattern3 = "yyyy-MM-dd'T'HH:mm:sszzz"
    }
}