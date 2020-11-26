package com.ic.flckr.feature.gallery.data

import androidx.room.withTransaction
import com.ic.flckr.feature.gallery.data.db.FlckrDatabase
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoEntity
import javax.inject.Inject

class PhotoLocalDataSource @Inject constructor(
    private val database: FlckrDatabase
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

//            if (searchQuery?.isNotEmpty() == true) {
//                database.getSearchSuggestionsDao().insert(SearchSuggestionEntity(searchText, timeProvider.currentTimeMillis()))
//            }
        }
    }
}