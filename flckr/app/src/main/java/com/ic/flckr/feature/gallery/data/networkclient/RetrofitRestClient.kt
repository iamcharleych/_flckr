package com.ic.flckr.feature.gallery.data.networkclient

import com.ic.flckr.network.core.DataRequest
import com.ic.flckr.network.core.DataResponse
import com.ic.flckr.network.core.RestClient
import com.ic.flckr.network.core.requireArgument
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoCollection
import com.ic.logger.Logger
import retrofit2.Retrofit

class RetrofitRestClient(retrofit: Retrofit) : RestClient<DataResponse<*>>() {

    private val api: API = retrofit.create(API::class.java)

    override suspend fun execute(request: DataRequest): DataResponse<*> {
        L.debug { "RetrofitRestClient.execute(): request=$request" }

        var response: DataResponse<*>? = null

        when (request.operation) {
            Requests.OP_GET_SEARCH -> {
                response = api.searchPhotos(
                    request.requireArgument(PARAM_API_KEY),
                    request.requireArgument(PARAM_SEARCH_TEXT),
                    request.requireArgument(PARAM_PAGE),
                ).let { retrofitResponse ->
                    DataResponse<FlckrPhotoCollection>().apply {
                        responseObject = retrofitResponse.photos
                        code = retrofitResponse.code ?: 0
                    }
                }
            }
            Requests.OP_GET_RECENT -> {
                response = api.getRecentPhotos(
                    request.requireArgument(PARAM_API_KEY)
                ).let { retrofitResponse ->
                    DataResponse<FlckrPhotoCollection>().apply {
                        responseObject = retrofitResponse.photos
                        code = retrofitResponse.code ?: 0
                    }
                }
            }
        }

        when (response?.code) {
//            ApiResponseCode.NO_CONTENT.code -> throw NoContentException()
//            ApiResponseCode.GONE.code -> throw SessionExpiredException()
        }

        return response?.apply { this.request = request } ?: throw Exception("Invalid Api operation!")
    }

    companion object {
        private val L = Logger()
    }
}
