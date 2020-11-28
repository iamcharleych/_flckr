package com.ic.flckr.feature.gallery.data

import androidx.room.withTransaction
import com.ic.flckr.common.data.db.FlckrDatabase
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoEntity
import com.ic.flckr.feature.gallery.data.entity.SearchSuggestionEntity
import com.ic.flckr.utils.TimeProvider
import javax.inject.Inject

class PhotoLocalDataSource @Inject constructor(
    private val database: FlckrDatabase,
    private val timeProvider: TimeProvider
) {

    suspend fun savePhotos(
        searchQuery: String?,
        photos: List<FlckrPhotoEntity>,
        clearBeforeSave: Boolean
    ) {
        database.withTransaction {
            if (clearBeforeSave) {
                database.getPhotosDao().deleteAll()
            }

            if (photos.isNotEmpty()) {
                database.getPhotosDao().insert(photos)
            }

            if (searchQuery?.isNotEmpty() == true) {
                database.getSearchSuggestionsDao().insert(SearchSuggestionEntity(
                    searchQuery,
                    timeProvider.currentTimeMillis()
                ))
            }
        }
    }

    suspend fun loadPhotos(): List<FlckrPhotoEntity> {
        return database.getPhotosDao().loadPhotos()
    }
}