package com.ic.flckr.feature.gallery.data.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoEntity

@Dao
interface FlckrPhotoDao : BaseEntityDao<FlckrPhotoEntity> {
    @Query("DELETE FROM photos")
    suspend fun deleteAll()

    @Query("SELECT * FROM photos")
    suspend fun loadPhotos(): DataSource.Factory<Int, FlckrPhotoEntity>
}