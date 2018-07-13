package com.example.abdellahberghachi.facephoto.ui.gallery

import android.Manifest
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.abdellahberghachi.facephoto.R
import com.example.abdellahberghachi.facephoto.utils.BaseActivity
import com.example.abdellahberghachi.facephoto.utils.adapters.PhotoListAdapter
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.activity_list_photo.*



class ListPhotoActivity : BaseActivity() {

    private var photoListAdapter: PhotoListAdapter? = null
    lateinit var listPhotoViewModel: ListPhotoViewModel
    private val MY_PERMISSIONS_REQUEST_READ_PHOTO = 2
    lateinit var itemConfirm: MenuItem
    val idAlbum: String by lazy {
        intent.getStringExtra("albums_id")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_photo)
    }


    override fun initEvents() {

        // add livedata to observe data from viewmodel
        listPhotoViewModel.getGallery(AccessToken.getCurrentAccessToken(), idAlbum).observe(this, Observer {
            if (it?.data != null) {
                progressBar_photo.visibility = View.GONE
                recycle_gallery.visibility = View.VISIBLE

                photoListAdapter?.submitList(it.data!!)

            }

        })

        // observe error
        listPhotoViewModel.getError().observe(this, Observer {
            progressBar_photo.visibility = View.GONE
            recycle_gallery.visibility = View.VISIBLE
            Toast.makeText(this, "Error occured please try again", Toast.LENGTH_SHORT).show()
        })

        listPhotoViewModel.getSaveImageResponse().observe(this, Observer {
            if (it != null && it) {
                photoListAdapter?.removeSelectedMap()
                showDialog()

            }
        })

    }

    // show loader when app start download image
    private fun showDialog() {
        progressBar_photo.visibility = View.GONE
        val builder1 = AlertDialog.Builder(this)
        builder1.setTitle(R.string.app_name)
        builder1.setMessage("Image(s) Saved.")
        builder1.setCancelable(true)
        builder1.setPositiveButton(
                "Close"
        ) { dialog, id -> dialog.cancel() }
        builder1.show()
    }

    // init UI
    override fun initUi() {
        recycle_gallery.layoutManager = GridLayoutManager(this, 4)
        recycle_gallery.setHasFixedSize(true)
        photoListAdapter = PhotoListAdapter(this, arrayListOf())
        recycle_gallery.adapter = photoListAdapter
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // init our viewmodel
    override fun initWi() {
        listPhotoViewModel = ViewModelProviders.of(this).get(ListPhotoViewModel::class.java)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_photo, menu)
        itemConfirm = menu.findItem(R.id.confirme)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        } else if (id == R.id.confirme) {
            if (ContextCompat.checkSelfPermission(this@ListPhotoActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == 0) {
                listPhotoViewModel.downloadImage(photoListAdapter?.getHashMap())
                progressBar_photo.visibility = View.VISIBLE

            } else {
                ActivityCompat.requestPermissions(this@ListPhotoActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_READ_PHOTO)

            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_PHOTO -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listPhotoViewModel.downloadImage(photoListAdapter?.getHashMap())
                    progressBar_photo.visibility = View.VISIBLE
                }
            }
        }
    }


}
