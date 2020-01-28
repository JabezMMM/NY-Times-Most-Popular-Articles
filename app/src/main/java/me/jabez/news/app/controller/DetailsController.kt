package me.jabez.news.app.controller

import android.view.View
import com.android.volley.toolbox.NetworkImageView
import me.jabez.news.app.DetailsControllerListener
import me.jabez.news.app.model.Results
import me.jabez.news.app.view.HeaderView

class DetailsController(private val results: Results, private val headerView: HeaderView, private val imageView: NetworkImageView, private val listener: DetailsControllerListener) : View.OnClickListener {
    override fun onClick(v: View?) {
        listener.onItemClicked(results, headerView, imageView)
    }
}