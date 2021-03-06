package com.example.myapp.hilt.retrofit

import retrofit2.http.GET

interface BlogRetrofit {
    @GET("blogs")
    suspend fun get(): List<BlogNetworkEntity>
}