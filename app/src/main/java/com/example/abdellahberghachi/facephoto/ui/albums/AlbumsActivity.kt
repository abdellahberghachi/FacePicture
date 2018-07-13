package com.example.abdellahberghachi.facephoto.ui.albums

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.example.abdellahberghachi.facephoto.R
import com.example.abdellahberghachi.facephoto.utils.BaseActivity
import com.facebook.AccessToken
import android.support.v7.widget.GridLayoutManager
import com.example.abdellahberghachi.facephoto.utils.adapters.AlbumsAdapter
import kotlinx.android.synthetic.main.activity_albums.*
import android.view.MenuItem
import android.view.View
import android.widget.Toast


class AlbumsActivity : BaseActivity() {

    lateinit var albumsViewModel: AlbumsViewModel
    private var albumsAdapter: AlbumsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

    }

    override fun initEvents() {
        albumsViewModel.requestAlbums(AccessToken.getCurrentAccessToken(), AccessToken.getCurrentAccessToken().userId).observe(this, Observer {

            if (it?.data != null) {
                progressBar_albums.visibility = View.GONE
                recycle_albums.visibility = View.VISIBLE

                albumsAdapter?.submitList(it.data!!)
            }
        })

        albumsViewModel.getError().observe(this, Observer {
            progressBar_albums.visibility = View.GONE
            recycle_albums.visibility = View.VISIBLE
            Toast.makeText(this, "Error occured please try again", Toast.LENGTH_SHORT).show()
        })

    }

    // int UI
    override fun initUi() {
        recycle_albums.layoutManager = GridLayoutManager(this, 2)
        recycle_albums.setHasFixedSize(true)
        albumsAdapter = AlbumsAdapter(this, arrayListOf())
        recycle_albums.adapter = albumsAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    //init ViewModel
    override fun initWi() {
        albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel::class.java)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}
