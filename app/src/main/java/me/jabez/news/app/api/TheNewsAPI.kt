package me.jabez.news.app.api

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.android.volley.VolleyError
import com.google.gson.Gson
import org.json.JSONObject
import me.jabez.news.app.util.MySingleton
import me.jabez.news.app.util.VolleyListener

abstract class TheNewsAPI : AsyncTask<Context, Void, Void>(), VolleyListener {
    companion object {
        private const val BASIC_URL = "https://api.nytimes.com/svc/mostpopular/v2/"
        private const val API_KEY = "dOlCynR3l03t14QVFpb8y0ruFhhSQkSN"
        private const val TAG = "TheNewsAPI"
    }

    override fun doInBackground(vararg params: Context): Void? {
        MySingleton.makeHttpRequest(params[0], getRequestUrl(), this)
        return null
    }

    private fun getRequestUrl(): String {
        return "${BASIC_URL}${getApiName()}${getSection()}/${getPeriod()}.json?api-key=${API_KEY}"
    }

    override fun onResponse(jsonObject: JSONObject) {
        onWebServiceLoadedListener(
                try {
                    Gson().fromJson(jsonObject.toString(), getReturnDataType())
                } catch (e: Exception) {
                    onError(TAG, e.toString())
                    //e.printStackTrace()
                }
        )
    }

    override fun onError(volleyError: VolleyError) {
        onError(TAG, volleyError.localizedMessage)
    }

    protected fun onError(name: String, message: String) {
        Log.e(name, message)
    }


    private fun getSection(): String {
        return "/all-sections"
    }

    abstract fun getApiName(): String
    abstract fun getPeriod(): Int
    abstract fun getReturnDataType(): Class<Any>
    abstract fun onWebServiceLoadedListener(anObject: Any?)
}