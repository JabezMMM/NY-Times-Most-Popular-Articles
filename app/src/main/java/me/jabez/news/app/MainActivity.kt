package me.jabez.news.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.jabez.news.app.model.MostViewedResponse
import me.jabez.news.app.model.Results
import me.jabez.news.app.api.Network
import me.jabez.news.app.view.adapter.NewsHeadingAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: NewsHeadingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        initListAdapter()
        fetchFeed(7)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_1 -> fetchFeed(1)
            R.id.app_bar_7 -> fetchFeed(7)
            R.id.app_bar_30 -> fetchFeed(30)
        }
        return true
    }

    private val callback = object : Callback<MostViewedResponse> {
        override fun onResponse(call: Call<MostViewedResponse>, response: Response<MostViewedResponse>) {
            isLoading(false)
            // Adapter values cleared & UI refreshed while fetching headlines
            mAdapter.clearList()
            loadHeadlines(ArrayList<Results>(response.body()!!.results))
        }

        override fun onFailure(call: Call<MostViewedResponse>, t: Throwable) {
            isLoading(false)
            Toast.makeText(this@MainActivity, if (t is IOException) {
                "No Internet Connection!"
            } else {
                "Something went wrong...Please try later!"
            }, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFeed(period: Int) {
        val call: Call<MostViewedResponse> = Network.getMostPopularService().getArticles(period)
        call.enqueue(callback)
        isLoading(true)
        headlineList.scrollTo(0, 0)
        appBar.setExpanded(true, true)
    }

    private fun initListAdapter() {
        mAdapter = NewsHeadingAdapter()
        headlineList.adapter = mAdapter
        headlineList.layoutManager = GridLayoutManager(this, 1)
    }

    private fun isLoading(visibility: Boolean) {
        loadingView.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun loadHeadlines(list: ArrayList<Results>) {
        mAdapter.appendToList(list)
        mAdapter.notifyDataSetChanged()
        isLoading(false)
    }
}

