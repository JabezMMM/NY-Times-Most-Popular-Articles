package me.jabez.news.app.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import me.jabez.news.app.model.Results
import me.jabez.news.app.view.HeadlineItemView

open class NewsHeadingAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var list: ArrayList<Any> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ListViewHolder(HeadlineItemView(parent.context))

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        holder as ListViewHolder
        when (item) {
            is Results -> with(holder.mView as HeadlineItemView) { setHeadlineValues(item) }
        }
    }

    inner class ListViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        override fun toString(): String = super.toString() + mView.tag
    }

    fun appendToList(a_list: ArrayList<Results>) {
        for (item in a_list) list.add(item)
    }

    fun clearList() {
        list = ArrayList()
    }
}

