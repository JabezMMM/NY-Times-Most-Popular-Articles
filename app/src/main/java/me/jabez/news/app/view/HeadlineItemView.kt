package me.jabez.news.app.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.item_headline.view.*
import me.jabez.news.app.R
import me.jabez.news.app.model.Results
import me.jabez.news.app.util.ImageRequester

// Line item in listing screen
class HeadlineItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {
    private val holder: View
    private lateinit var listener: OnClickListener

    init {
        val layoutInflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        holder = layoutInflater.inflate(R.layout.item_headline, this@HeadlineItemView)
    }

    fun setHeadlineValues(article: Results) {
        listener = DetailsListener(article)
        holder.itemHeader.setValues(article, listener)
        holder.headlineImage.setOnClickListener(listener)
        val urlToImage = article.media[0].media_metadata[0].url
        if (urlToImage != null)
            ImageRequester.setImageFromUrl(holder.headlineImage, urlToImage)
        else
            ImageRequester.setImageFromUrl(holder.headlineImage, ImageRequester.PLACEHOLDER_URL)
    }

    // Navigate to details screen
    private inner class DetailsListener(val article: Results) : OnClickListener {
        override fun onClick(v: View?) {
            val activity = context as android.app.Activity
            val intent =
                    android.content.Intent(context, me.jabez.news.app.HeadlineDetailsActivity::class.java)
            intent.putExtra("article", article)
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                activity.startActivity(intent, getTransitionBundle())
            } else {
                activity.startActivity(intent)
            }
        }
    }

    // Shows transition animation if API-21 and above
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getTransitionBundle(): Bundle? {
        holder.itemHeader.transitionName = "robot1"
        holder.headlineImage.transitionName = "robot2"
        val titleAnim: Pair<android.view.View, kotlin.String> =
                Pair.create(holder.itemHeader, "robot1")
        val imageAnim: Pair<android.view.View, kotlin.String> =
                Pair.create(holder.headlineImage, "robot2")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as android.app.Activity,
                titleAnim,
                imageAnim
        )
        return options.toBundle()
    }
}