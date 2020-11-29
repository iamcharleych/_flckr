package com.ic.flckr.feature.gallery.data.entity

import androidx.room.Entity

@Entity(tableName = "photos", primaryKeys = ["id"])
data class FlckrPhotoEntity(
    val id: Long,
    val secret: String,
    val server: String,
    val farm: Int
)