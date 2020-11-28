package com.ic.flckr.feature.gallery.data

import com.ic.flckr.common.data.network.core.DataResponse
import com.ic.flckr.common.data.network.core.RestClient
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoCollection
import com.ic.flckr.feature.gallery.data.networkclient.Requests
import javax.inject.Inject

class PhotoNetworkDataSource @Inject constructor(
    private val restClient: RestClient<DataResponse<*>>
) {

    suspend fun getPhotos(searchQuery: String?, page: Int): FlckrPhotoCollection {
        val request = if (searchQuery?.isNotEmpty() == true) {
            Requests.search(searchQuery, page)
        } else {
            Requests.getRecentPhotos()
        }

        return restClient.execute(request).responseObject as FlckrPhotoCollection
    }
}