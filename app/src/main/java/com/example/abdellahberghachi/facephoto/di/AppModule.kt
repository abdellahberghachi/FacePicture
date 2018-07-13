package com.example.abdellahberghachi.facephoto.di

import android.app.Application
import com.example.abdellahberghachi.facephoto.data.RepositoryDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun providesRepository(): RepositoryDataSource {
        return RepositoryDataSource()
    }
}