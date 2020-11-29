package com.ic.flckr.feature.gallery.data.networkclient

import com.ic.flckr.feature.gallery.data.entity.FlckrPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1")
    suspend fun searchPhotos(
        @Query("api_key") apiKey: String,
        @Query("text") searchQuery: String,
        @Query("page") page: Int
    ): FlckrPhotosResponse

    @GET("?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    suspend fun getRecentPhotos(
        @Query("api_key") apiKey: String
    ): FlckrPhotosResponse
}
