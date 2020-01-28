package me.jabez.news.app.util

import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    @Throws(Exception::class)
    fun checkTimestampFormatter() {
        val currentTime = DateFormatter.getFormattedDate("2019-01-13T17:01:00Z")
        var nextTime = DateFormatter.getFormattedDate("2019-01-13T17:01:00Z")

        assertEquals("Moments ago", DateFormatter.getDifference(currentTime, nextTime))

        nextTime = DateFormatter.getFormattedDate("2019-01-13T16:51:00Z")
        assertEquals("10 minutes ago", DateFormatter.getDifference(currentTime, nextTime))

        nextTime = DateFormatter.getFormattedDate("2019-01-13T16:01:00Z")
        assertEquals("1 hour ago", DateFormatter.getDifference(currentTime, nextTime))

        nextTime = DateFormatter.getFormattedDate("2019-01-13T15:00:00Z")
        assertEquals("2 hours ago", DateFormatter.getDifference(currentTime, nextTime))

        nextTime = DateFormatter.getFormattedDate("2019-01-12T17:00:00Z")
        assertEquals("1 day ago", DateFormatter.getDifference(currentTime, nextTime))

        nextTime = DateFormatter.getFormattedDate("2019-01-10T17:00:00Z")
        assertEquals("3 days ago", DateFormatter.getDifference(currentTime, nextTime))
    }
}