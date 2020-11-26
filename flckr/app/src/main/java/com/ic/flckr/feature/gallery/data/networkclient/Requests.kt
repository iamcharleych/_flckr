package com.ic.flckr.feature.gallery.data.networkclient

import com.ic.flckr.network.core.DataRequest

object Requests {

    /**
     * available api operations
     *
     * Please, for new operations use format: OP_(GET|POST|PUT|...)_<Operation name>
    </Operation> */

    const val OP_GET_SEARCH = "op_get_search"

    fun search(searchQuery: String, page: Int): DataRequest {
        return DataRequest(OP_GET_SEARCH)
            .with(PARAM_SEARCH_TEXT, searchQuery)
            .with(PARAM_PAGE, page.toString())
    }

    const val OP_GET_RECENT = "op_get_recent"

    fun getRecentPhotos(): DataRequest {
        return DataRequest(OP_GET_RECENT)
    }
}
