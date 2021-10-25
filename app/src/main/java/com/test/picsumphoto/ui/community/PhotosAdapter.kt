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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        Const.ItemViewType.TYPE_1 ->
            PhpotosViewHolder(R.layout.item_photo_1.inflate(parent))
        Const.ItemViewType.TYPE_2 ->
            PhpotosViewHolder(R.layout.item_photo_2.inflate(parent))
        else ->
            PhpotosViewHolder(R.layout.item_photo_1.inflate(parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        holder as PhpotosViewHolder
        item.run {
            holder.photo.load(item.download_url ?: "")

            holder.author.text = item.author
        }
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) Const.ItemViewType.TYPE_1 else Const.ItemViewType.TYPE_2
    }

    inner class PhpotosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo = view.findViewById<ImageView>(R.id.photo)
        val author = view.findViewById<TextView>(R.id.author)
        val rootView = view.findViewById<LinearLayout>(R.id.rootView)
    }

    companion object {
        const val TAG = "PhotosAdapter"
    }
}