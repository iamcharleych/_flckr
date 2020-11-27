package com.ic.flckr.feature.gallery.data

import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.PhotoRepository
import com.ic.flckr.feature.gallery.domain.model.Photo
import com.ic.flckr.utils.Reachability
import com.ic.logger.Logger
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val mapper: EntityMapper,
    private val photoNetworkDataSource: PhotoNetworkDataSource,
    private val photoLocalDataSource: PhotoLocalDataSource,
    private val reachability: Reachability
): PhotoRepository {

    override suspend fun search(
        searchQuery: String?,
        pageIndex: Int,
        appendResults: Boolean
    ): Result<List<Photo>> {
        return try {
            if (!reachability.isConnected() && searchQuery.isNullOrEmpty()) {
                // case of cached 'Recent photos': no internet and empty search term
                val photos = photoLocalDataSource.loadPhotos()

                // early exit
                return Result.Success(photos.map { mapper.mapPhoto(it) })
            }

            val entityCollection = photoNetworkDataSource.getPhotos(searchQuery, pageIndex)
            val photos = entityCollection.photo ?: emptyList()

            try {
                // no need to cache search results. Manage only the 'Recent photos' case
                if (searchQuery.isNullOrEmpty()) {
                    photoLocalDataSource.savePhotos(searchQuery, photos, !appendResults)
                }
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