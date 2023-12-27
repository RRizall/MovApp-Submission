package com.rzl.movapp.core.data.source.remote.network

import com.rzl.movapp.core.data.source.remote.response.ListPopularResponse

import retrofit2.http.GET


interface ApiService {
        @GET("movie/popular")
        suspend fun getAllPopular(): ListPopularResponse

}



