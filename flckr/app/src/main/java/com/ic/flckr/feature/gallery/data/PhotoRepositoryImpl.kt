package com.ic.flckr.feature.gallery.data

import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.PhotoRepository
import com.ic.flckr.feature.gallery.domain.model.Photo
import com.ic.logger.Logger
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val mapper: EntityMapper,
    private val photoNetworkDataSource: PhotoNetworkDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource
): PhotoRepository {

    override suspend fun search(
        searchQuery: String?,
        pageIndex: Int,
        appendResults: Boolean
    ): Result<List<Photo>> {
        return try {
            val entityCollection = photoNetworkDataSource.getPhotos(searchQuery, pageIndex)
            val photos = entityCollection.photo ?: emptyList()

            try {
                photoLocalDataSource.savePhotos(searchQuery, photos, !appendResults)
            } catch (err: Exception) {
                L.warn { "Failed to save just fetched photos to database. Error: ${err.localizedMessage}" }
            }

            Result.Success(photos.map { mapper.mapPhoto(it) })
        } catch (e: Exception) {
            Result.Fail("Failed to fetch photos", e)
        }
    }

    companion object {
        private val L = Logger()
    }
}