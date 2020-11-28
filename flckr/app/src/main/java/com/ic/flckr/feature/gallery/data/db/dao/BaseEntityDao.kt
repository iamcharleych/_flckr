package com.ic.flckr.feature.gallery.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseEntityDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entities: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)
}
