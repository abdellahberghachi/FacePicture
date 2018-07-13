package com.example.abdellahberghachi.facephoto.utils.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.abdellahberghachi.facephoto.R
import com.example.abdellahberghachi.facephoto.ui.gallery.ListPhotoActivity
import com.example.abdellahberghachi.facephoto.utils.POJO.Albums
import timber.log.Timber


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {


    var context: Context
    var albums: List<Albums>


    constructor(context: Context, albums: List<Albums>) : super() {
        this.context = context
        this.albums = albums

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.albums_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val albums = albums[position]
        holder.tvAlbumsName.text = albums.name
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, ListPhotoActivity::class.java).putExtra("albums_id", albums.id))
        }
    }


    open class ViewHolder(internal var view: View) : RecyclerView.ViewHolder(view) {
        var tvAlbumsName: TextView = view.findViewById<View>(R.id.tv_albumName) as TextView

    }

    // sort list by name before showing it in the recyclerview
    fun submitList(list: List<Albums>) {
        albums = list.sortedBy { it.name.toString().toLowerCase() }
        notifyDataSetChanged()
    }


}