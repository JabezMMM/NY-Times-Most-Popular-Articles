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
import me.jabez.news.app.DetailsControllerListener
import me.jabez.news.app.R
import me.jabez.news.app.controller.DetailsController
import me.jabez.news.app.model.Results
import me.jabez.news.app.util.ImageRequester

// Line item in listing screen
class HeadlineItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {
    private val holder: View

    init {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        holder = layoutInflater.inflate(R.layout.item_headline, this@HeadlineItemView)
    }

    fun setHeadlineValues(article: Results, listener: DetailsControllerListener) {
        val controller = DetailsController(article, holder.itemHeader, holder.headlineImage, listener)
        holder.itemHeader.setValues(article, controller)
        holder.headlineImage.setOnClickListener(controller)
        val urlToImage = article.media[0].media_metadata[0].url
        if (urlToImage != null)
            ImageRequester.setImageFromUrl(holder.headlineImage, urlToImage)
        else
            ImageRequester.setImageFromUrl(holder.headlineImage, ImageRequester.PLACEHOLDER_URL)
    }
}