package me.jabez.news.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.jabez.news.app.api.MostViewedAPI
import me.jabez.news.app.model.Results
import me.jabez.news.app.view.adapter.NewsHeadingAdapter

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

    private fun fetchFeed(days: Int) {
        //appBar.replaceMenu(R.menu.appBar_menu)
        GetTopHeadlines(days).execute(this)

        // Adapter values cleared & UI refreshed while fetching headlines
        mAdapter.clearList()
        mAdapter.notifyDataSetChanged()
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

    /* API calls */
    private inner class GetTopHeadlines(days: Int) : MostViewedAPI(days) {
        override fun onListFetched(list: ArrayList<Results>) {
            mAdapter.appendToList(list)
            mAdapter.notifyDataSetChanged()
            isLoading(false)
        }
    }
}

