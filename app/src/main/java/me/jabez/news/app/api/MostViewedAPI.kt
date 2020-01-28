package me.jabez.news.app.api

import me.jabez.news.app.model.MostViewedResponse
import me.jabez.news.app.model.Results
import kotlin.collections.ArrayList

abstract class MostViewedAPI(private val days: Int) : TheNewsAPI() {
    companion object {
        private const val API_NAME = "mostviewed"
    }

    override fun getApiName(): String {
        return API_NAME
    }

    override fun getReturnDataType(): Class<Any> {
        return MostViewedResponse::class.java as Class<Any>
    }

    override fun onWebServiceLoadedListener(anObject: Any?) {
        when (anObject) {
            is MostViewedResponse -> onListFetched(ArrayList<Results>(anObject.results))
            else -> onError(API_NAME, "$anObject")
        }
    }

    override fun getPeriod(): Int {
        return days
    }

    abstract fun onListFetched(list: ArrayList<Results>)
}

