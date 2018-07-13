package com.example.abdellahberghachi.facephoto.di

import android.app.Application
import com.example.abdellahberghachi.facephoto.ui.albums.AlbumsViewModel
import com.example.abdellahberghachi.facephoto.ui.gallery.ListPhotoViewModel

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(listPhotoViewModel: ListPhotoViewModel)
    fun inject(listPhotoViewModel: AlbumsViewModel)

}