package me.jabez.news.app

import me.jabez.news.app.api.Network
import org.junit.Assert
import org.junit.Test

class MyTest {

    private fun callTheApi(httpCode: Int, period: Int) {
        val response = Network.getMostPopularService().getArticles(period).execute()
        Assert.assertTrue(response.code() == httpCode)
    }

    @Test
    fun `check valid periods`() {
        val httpCode = 200
        callTheApi(httpCode, 1)
        callTheApi(httpCode, 7)
        callTheApi(httpCode, 30)
    }

    @Test
    fun `check invalid periods`() {
        val httpCode = 400
        callTheApi(httpCode, 2)
        callTheApi(httpCode, 8)
        callTheApi(httpCode, 29)
    }
}