package me.jabez.news.app.util

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache

import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import me.jabez.news.app.NewsApplication

/**
 * Class that handles image requests using Volley.
 */
object ImageRequester {
    private val requestQueue: RequestQueue
    private val imageLoader: ImageLoader
    private val maxByteSize: Int
    const val PLACEHOLDER_URL: String = "https://1000logos.net/wp-content/uploads/2017/04/Symbol-New-York-Times.png" //Image-URL when valid URL not provided.

    init {
        val context = NewsApplication.instance
        requestQueue = Volley.newRequestQueue(context)
        requestQueue.start()
        maxByteSize =
                calculateMaxByteSize(context)
        imageLoader = ImageLoader(
                requestQueue,
                object : ImageLoader.ImageCache {
                    private val lruCache = object : LruCache<String, Bitmap>(maxByteSize) {
                        override fun sizeOf(url: String, bitmap: Bitmap): Int {
                            return bitmap.byteCount
                        }
                    }

                    @Synchronized
                    override fun getBitmap(url: String): Bitmap? {
                        return lruCache.get(url)
                    }

                    @Synchronized
                    override fun putBitmap(url: String, bitmap: Bitmap) {
                        lruCache.put(url, bitmap)
                    }
                })
    }

    fun setImageFromUrl(networkImageView: NetworkImageView, url: String) {
        networkImageView.setImageUrl(url, imageLoader)
    }

    private fun calculateMaxByteSize(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenBytes = displayMetrics.widthPixels * displayMetrics.heightPixels * 4
        return screenBytes * 3
    }
}