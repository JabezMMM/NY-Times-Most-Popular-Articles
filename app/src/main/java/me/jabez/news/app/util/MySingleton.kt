package me.jabez.news.app.util

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object MySingleton {
    private const val TAG = "Volley requests"
    private var queue: RequestQueue? = null

    fun makeHttpRequest(context: Context, requestUrl: String, listener: VolleyListener) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, requestUrl, null,
                Response.Listener { response ->
                    Log.d(TAG, "Response: %s".format(response.toString()))
                    listener.onResponse(response)
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, error.localizedMessage)
                    listener.onError(error)
                }
        )

        // Access the RequestQueue through your singleton class.
        getRequestQueue(context).add(jsonObjectRequest)
    }

    private fun getRequestQueue(context: Context): RequestQueue {
        return if (queue == null) Volley.newRequestQueue(context) else queue!!
    }
}

interface VolleyListener {
    fun onResponse(jsonObject: JSONObject)
    fun onError(volleyError: VolleyError)
}