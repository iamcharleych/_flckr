package com.ic.flckr.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ic.flckr.feature.gallery.data.db.dao.FlckrPhotoDao
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoEntity

const val DATABASE_NAME = "flckr_database.db"

@Database(
    entities = [
        FlckrPhotoEntity::class,
//        SearchSuggestionEntity::class
    ], version = 1, exportSchema = false
)
abstract class FlckrDatabase : RoomDatabase() {
    abstract fun getPhotosDao(): FlckrPhotoDao
//    abstract fun getSearchSuggestionsDao(): SearchSuggestionsDao
}
