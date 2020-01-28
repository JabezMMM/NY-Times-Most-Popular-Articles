package me.jabez.news.app

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.NetworkImageView
import kotlinx.android.synthetic.main.activity_main.*
import me.jabez.news.app.model.MostViewedResponse
import me.jabez.news.app.model.Results
import me.jabez.news.app.api.Network
import me.jabez.news.app.controller.DetailsController
import me.jabez.news.app.view.HeaderView
import me.jabez.news.app.view.adapter.NewsHeadingAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MainActivity : AppCompatActivity(), DetailsControllerListener {
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
            loadHeadlines(ArrayList<Results>(response.body()!!.results))
        }

        override fun onFailure(call: Call<MostViewedResponse>, t: Throwable) {
            isLoading(false)
            infoText.text = if (t is IOException) {
                "No Internet Connection!"
            } else {
                "Something went wrong...Please try later!"
            }
            infoText.visibility = View.VISIBLE
        }
    }

    private fun fetchFeed(period: Int) {
        // Adapter values cleared & UI refreshed while fetching headlines
        mAdapter.clearList()
        infoText.visibility = View.GONE
        mAdapter.notifyDataSetChanged()
        isLoading(true)

        val call: Call<MostViewedResponse> = Network.getMostPopularService().getArticles(period)
        call.enqueue(callback)

        headlineList.scrollTo(0, 0)
        appBar.setExpanded(true, true)
    }

    private fun initListAdapter() {
        mAdapter = NewsHeadingAdapter(this)
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

    override fun onItemClicked(results: Results, vararg view: View) {
        val intent = android.content.Intent(this, me.jabez.news.app.HeadlineDetailsActivity::class.java)
        intent.putExtra("article", results)
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            startActivity(intent, getTransitionBundle(view[0], view[1]))
        } else {
            startActivity(intent)
        }
    }

    // Shows transition animation if API-21 and above
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getTransitionBundle(vararg view: View): Bundle? {
        view[0].transitionName = "robot1"
        view[1].transitionName = "robot2"
        val titleAnim: Pair<View, String> = Pair.create(view[0], "robot1")
        val imageAnim: Pair<View, String> = Pair.create(view[1], "robot2")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, titleAnim, imageAnim)
        return options.toBundle()
    }
}

