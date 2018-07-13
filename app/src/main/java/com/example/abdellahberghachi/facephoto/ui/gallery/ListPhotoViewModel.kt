package com.example.abdellahberghachi.facephoto.ui.gallery

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import com.bumptech.glide.Glide
import com.example.abdellahberghachi.facephoto.App
import com.example.abdellahberghachi.facephoto.data.RepositoryDataSource
import com.example.abdellahberghachi.facephoto.utils.POJO.GalleryPojo
import com.facebook.AccessToken
import javax.inject.Inject
import android.graphics.Bitmap
import android.net.Uri
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.abdellahberghachi.facephoto.utils.POJO.Image
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ListPhotoViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repositoryDataSource: RepositoryDataSource
    var number: Int = 0

    var saveImageReponse: MutableLiveData<Boolean> = MutableLiveData()

    init {
        App.mAppComponent.inject(this)
    }

    fun getGallery(accessToken: AccessToken, idAlbum: String): LiveData<GalleryPojo> = repositoryDataSource.fetchImages(accessToken, idAlbum)

    fun getError(): LiveData<Boolean> = repositoryDataSource.getErroGallery()

    //download selected image as bitmap and store it to internal storage
    fun downloadImage(hashMap: HashMap<Int, Image>?) {
        hashMap?.forEach { (_, value) ->
            Glide.with(getApplication() as App)
                    .asBitmap()
                    .load(value.source)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            saveImage(resource)
                            number++
                            if (number == hashMap.size) {
                                saveImageReponse.postValue(true)
                            }
                        }
                    })
        }


    }

    // save image into internal storage
    fun saveImage(image: Bitmap): String? {

        var savedImagePath: String? = null
        val storageDir: File = File(
                Environment.getExternalStorageDirectory().toString() + "/FacePicture")
        val imageFileName = "PNG_${UUID.randomUUID().toString()}.png"

        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
        }
        galleryAddPic(savedImagePath)
        return savedImagePath

    }

    private fun galleryAddPic(imagePath: String?) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        (getApplication() as App).sendBroadcast(mediaScanIntent)
    }


    fun getSaveImageResponse(): LiveData<Boolean> = saveImageReponse


}