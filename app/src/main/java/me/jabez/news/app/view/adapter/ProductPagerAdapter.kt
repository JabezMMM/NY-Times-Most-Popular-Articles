package me.jabez.news.app.view.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.android.volley.toolbox.NetworkImageView
import me.jabez.news.app.util.ImageRequester

class ProductPagerAdapter constructor(private var mContext: Context, private var images: ArrayList<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view === p1
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val productImage = NetworkImageView(mContext)
        val params = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        productImage.layoutParams = params
        productImage.scaleType = ImageView.ScaleType.FIT_END
        productImage.scaleType = ImageView.ScaleType.CENTER_CROP

        if (images.size > 0) ImageRequester.setImageFromUrl(productImage, images[position])
        collection.addView(productImage)
        return productImage
    }



    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ""
    }
}