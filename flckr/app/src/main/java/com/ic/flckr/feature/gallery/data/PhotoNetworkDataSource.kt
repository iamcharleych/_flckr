package com.ic.flckr.feature.gallery.data

import com.ic.flckr.feature.gallery.data.networkclient.Requests
import com.ic.flckr.feature.gallery.data.networkclient.RetrofitRestClient
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoCollection
import javax.inject.Inject

class PhotoNetworkDataSource @Inject constructor(
    private val retrofitRestClient: RetrofitRestClient
) {

    suspend fun getPhotos(searchQuery: String?, page: Int): FlckrPhotoCollection {
        val request = if (searchQuery?.isNotEmpty() == true) {
            Requests.search(searchQuery, page)
        } else {
            Requests.getRecentPhotos()
        }

        return retrofitRestClient.execute(request).responseObject as FlckrPhotoCollection
    }
}