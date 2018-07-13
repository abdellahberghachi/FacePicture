package com.example.abdellahberghachi.facephoto.utils.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.example.abdellahberghachi.facephoto.R
import com.example.abdellahberghachi.facephoto.ui.gallery.ListPhotoActivity
import com.example.abdellahberghachi.facephoto.ui.preview.PreviewImageActivity

import com.example.abdellahberghachi.facephoto.utils.POJO.Gallery
import com.example.abdellahberghachi.facephoto.utils.POJO.Image


class PhotoListAdapter : RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {


    var context: Context
    var images: List<Gallery>
    var selectedImage: HashMap<Int, Image> = HashMap()

    constructor(context: Context, images: List<Gallery>) : super() {
        this.context = context
        this.images = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(images[position].images?.get(0)?.source).into(holder.ivImage)

        holder.itemView.setOnLongClickListener {
            onImageClicked(position, holder)
            true

        }
        holder.itemView.setOnClickListener {
            if (selectedImage.size == 0) {
                (context as ListPhotoActivity).itemConfirm.isVisible = false
                context.startActivity(Intent(context, PreviewImageActivity::class.java).putExtra("url", images[position].images?.get(0)?.source))

            } else {
                onImageClicked(position, holder)
            }
        }

    }

    private fun onImageClicked(position: Int, holder: ViewHolder) {
        if (selectedImage[position] != null) {
            selectedImage.remove(position)
            holder.checkbox.isChecked = false
            holder.checkbox.visibility = View.GONE
            if (selectedImage.size == 0) {
                (context as ListPhotoActivity).itemConfirm.isVisible = false
            }

        } else {
            selectedImage[position] = images[position].images?.get(0)!!
            holder.checkbox.isChecked = true
            holder.checkbox.visibility = View.VISIBLE
            (context as ListPhotoActivity).itemConfirm.isVisible = true
        }

    }


    open class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {
        var ivImage: ImageView = view.findViewById<View>(R.id.iv_gallery) as ImageView
        var checkbox: CheckBox = view.findViewById<View>(R.id.check_box) as CheckBox

    }


    fun submitList(newList: List<Gallery>) {
        images = newList
        notifyDataSetChanged()
    }

    fun getHashMap(): HashMap<Int, Image> = selectedImage

    fun removeSelectedMap() {
        selectedImage.clear()
        (context as ListPhotoActivity).itemConfirm.isVisible = false
        notifyDataSetChanged()
    }
}