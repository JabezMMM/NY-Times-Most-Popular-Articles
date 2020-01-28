package me.jabez.news.app

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_headline_details.*
import me.jabez.news.app.model.Results
import me.jabez.news.app.util.ImageRequester
import me.jabez.news.app.view.adapter.ProductPagerAdapter

class HeadlineDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_headline_details)
        val article = intent.getSerializableExtra("article") as Results
        headerView.setValues(article)

        val images = article.media[0].media_metadata.filter { it.height == 320 }
        val imageList = ArrayList<String>()
        for (index in images) {
            val urlToImage = index.url ?: ImageRequester.PLACEHOLDER_URL
            imageList.add(urlToImage)
        }
        productImages.adapter = ProductPagerAdapter(this, imageList)

        content.movementMethod = LinkMovementMethod.getInstance()
        val contentText = setContent(article)
        content.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(contentText, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(contentText)
        }

        author.text = if (article.source.isNullOrBlank()) {
            author.visibility = View.GONE
            ""
        } else {
            article.source
        }
        closeDetails.setOnClickListener { onBackPressed() }
    }

    private fun setContent(article: Results): String {
        val replacementText = "<a href=\"${article.url}\">Read more</a>"
        val content = article.abstract
        return if (content.isNullOrBlank()) replacementText
        else {
            if (content.contains("[+")) {
                val startIndex = content.indexOf("[+")
                content.replaceRange(startIndex, content.length, replacementText)
            } else {
                "$content $replacementText"
            }
        }
    }
}