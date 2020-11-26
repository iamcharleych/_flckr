package com.ic.flckr.feature.gallery.domain

import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.model.Photo

interface PhotoRepository {
    suspend fun search(
        searchQuery: String?,
        pageIndex: Int,
        appendResults: Boolean
    ): Result<List<Photo>>
}
