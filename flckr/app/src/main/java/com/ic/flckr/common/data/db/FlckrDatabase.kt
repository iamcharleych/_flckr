package com.ic.flckr.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ic.flckr.feature.gallery.data.db.dao.FlckrPhotoDao
import com.ic.flckr.feature.gallery.data.db.dao.SearchSuggestionsDao
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoEntity
import com.ic.flckr.feature.gallery.data.entity.SearchSuggestionEntity

const val DATABASE_NAME = "flckr_database.db"

@Database(
    entities = [
        FlckrPhotoEntity::class,
        SearchSuggestionEntity::class
    ], version = 2, exportSchema = false
)
abstract class FlckrDatabase : RoomDatabase() {
    abstract fun getPhotosDao(): FlckrPhotoDao
    abstract fun getSearchSuggestionsDao(): SearchSuggestionsDao
}
