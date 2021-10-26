package com.test.picsumphoto.ui.community

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.picsumphoto.Const
import com.test.picsumphoto.R
import com.test.picsumphoto.data.model.Photo
import com.test.picsumphoto.extension.dp2px
import com.test.picsumphoto.extension.inflate
import com.test.picsumphoto.extension.load
import com.test.picsumphoto.ui.MainActivity


/**
 * @ClassName PhotosAdapter
 * @Description Photos Adapter
 * @Author mailo
 * @Date 2021/10/25
 */
class PhotosAdapter(val activity: MainActivity, var dataList: List<Photo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    public var type : Int = Const.ItemViewType.TYPE_2
        get() = field
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (type) {
        Const.ItemViewType.TYPE_2 ->
            FeedingViewHolder(R.layout.item_photo_2.inflate(parent))
        Const.ItemViewType.TYPE_1 ->
            NormalViewHolder(R.layout.item_photo_1.inflate(parent))
        else ->
            FeedingViewHolder(R.layout.item_photo_2.inflate(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        if (type == Const.ItemViewType.TYPE_2){
            holder as FeedingViewHolder
            item.run {
                holder.photo.load(item.download_url ?: "")
            }
        }else if (type == Const.ItemViewType.TYPE_1){
            holder as NormalViewHolder
            item.run {
                holder.photo.load(item.download_url ?: "")

                holder.author.text = item.author
            }
        }



    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return type
    }

    inner class FeedingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo = view.findViewById<ImageView>(R.id.photo)
        val rootView = view.findViewById<LinearLayout>(R.id.rootView)
    }

    inner class NormalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo = view.findViewById<ImageView>(R.id.photo)
        val author = view.findViewById<TextView>(R.id.author)
        val rootView = view.findViewById<LinearLayout>(R.id.rootView)
    }

    companion object {
        const val TAG = "PhotosAdapter"
    }
}