package me.jabez.news.app.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_header.view.*
import me.jabez.news.app.R
import me.jabez.news.app.model.Results

// Used commonly while listing and in details screens
class HeaderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : LinearLayout(context, attrs, defStyle) {
    private val holder: View

    init {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        holder = layoutInflater.inflate(R.layout.view_header, this)
    }

    fun setValues(article: Results) {
        holder.headingTitle.text = article.title
        holder.byAuthor.text = article.byline
        //holder.publishedAt.text = DateFormatter.getTime(article.published_date!!)
        holder.publishedAt.text = article.published_date
    }

    fun setValues(article: Results, listener: OnClickListener) {
        setValues(article)
        holder.headingTitle.setOnClickListener(listener)
    }
}