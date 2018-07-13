package com.example.abdellahberghachi.facephoto.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import com.example.abdellahberghachi.facephoto.utils.POJO.AlbumsPojo
import com.example.abdellahberghachi.facephoto.utils.POJO.GalleryPojo
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.google.gson.Gson
import timber.log.Timber

// this class represent data layer
class RepositoryDataSource {

    var erroResponseAlbums: MutableLiveData<Boolean> = MutableLiveData()
    val erroResponseGallery: MutableLiveData<Boolean> = MutableLiveData()


    // this function allow us to fetch user albums
    fun requestAlbums(accessToken: AccessToken, userId: String): LiveData<AlbumsPojo> {

        var albums: MutableLiveData<AlbumsPojo> = MutableLiveData()
        GraphRequest(
                accessToken,
                "/$userId/albums",
                null,
                HttpMethod.GET,
                GraphRequest.Callback {
                    Timber.e(it.toString())
                    if (it?.error != null) {
                        erroResponseAlbums.postValue(true)
                    } else {
                        val albumsResponse = Gson().fromJson(it.rawResponse, AlbumsPojo::class.java)
                        albums.postValue(albumsResponse)
                    }

                }
        ).executeAsync()
        return albums
    }


    //fetch list of image from a selected albums
    fun fetchImages(accessToken: AccessToken, idAlbum: String): LiveData<GalleryPojo> {
        val gallery: MutableLiveData<GalleryPojo> = MutableLiveData()

        val parameters = Bundle()
        parameters.putString("fields", "images")
        GraphRequest(
                accessToken,
                "/$idAlbum/photos",
                parameters,
                HttpMethod.GET,
                GraphRequest.Callback {
                    if (it?.error != null) {
                        erroResponseGallery.postValue(true)
                    } else {
                        val albumsResponse = Gson().fromJson(it.rawResponse, GalleryPojo::class.java)


                        Timber.e(albumsResponse.data?.get(0)?.images.toString())
                        gallery.postValue(albumsResponse)
                    }
                }
        ).executeAsync()
        return gallery
    }


    fun getErroAlbums(): LiveData<Boolean> = erroResponseAlbums

    fun getErroGallery(): LiveData<Boolean> = erroResponseGallery
}