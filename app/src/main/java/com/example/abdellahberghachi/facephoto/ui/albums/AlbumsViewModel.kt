package com.example.abdellahberghachi.facephoto.ui.albums

import android.arch.lifecycle.LiveData

import android.arch.lifecycle.ViewModel

import com.example.abdellahberghachi.facephoto.App
import com.example.abdellahberghachi.facephoto.data.RepositoryDataSource
import com.example.abdellahberghachi.facephoto.utils.POJO.AlbumsPojo
import com.facebook.AccessToken
import javax.inject.Inject


class AlbumsViewModel : ViewModel() {


    @Inject
    lateinit var repositoryDataSource: RepositoryDataSource


    init {
        App.mAppComponent.inject(this)
    }

    fun requestAlbums(accessToken: AccessToken, userId: String): LiveData<AlbumsPojo> = repositoryDataSource.requestAlbums(accessToken, userId)

    fun getError(): LiveData<Boolean> = repositoryDataSource.getErroAlbums()



}