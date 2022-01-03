package com.example.myapp.hilt.di

import com.example.myapp.hilt.repository.MainRepository
import com.example.myapp.hilt.retrofit.BlogRetrofit
import com.example.myapp.hilt.retrofit.NetworkMapper
import com.example.myapp.hilt.room.BlogDao
import com.example.myapp.hilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}