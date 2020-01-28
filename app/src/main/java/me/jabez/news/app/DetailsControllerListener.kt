package me.jabez.news.app

import android.view.View
import com.android.volley.toolbox.NetworkImageView
import me.jabez.news.app.model.Results
import me.jabez.news.app.view.HeaderView

interface DetailsControllerListener {
    fun onItemClicked(results: Results, vararg view: View)
}