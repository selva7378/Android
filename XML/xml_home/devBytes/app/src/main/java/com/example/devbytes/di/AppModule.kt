package com.example.devbytes.di

import android.content.Context
import androidx.room.Room
import com.example.devbytes.DevByteApplication
import com.example.devbytes.network.DevbyteService
import com.example.devbytes.network.Network
import com.example.devbytes.repository.VideosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import com.example.devbytes.database.VideosDatabase
import dagger.hilt.components.SingletonComponent
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VideosDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            VideosDatabase::class.java,
            "videos"
        ).build()

    @Provides
    @Singleton
    fun provideDevbyteService(): DevbyteService = Network.devbytes

    @Provides
    @Singleton
    fun provideVideosRepository(
        database: VideosDatabase,
//        service: DevbyteService
    ): VideosRepository = VideosRepository(database)
}

