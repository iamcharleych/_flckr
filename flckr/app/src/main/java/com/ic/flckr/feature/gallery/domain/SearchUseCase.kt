package com.ic.flckr.feature.gallery.domain

import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.model.Photo
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(
        searchQuery: String?,
        pageIndex: Int,
        appendResults: Boolean
    ): Result<List<Photo>> {
        return repository.search(searchQuery, pageIndex, appendResults)
    }
}