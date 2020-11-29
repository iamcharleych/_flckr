package com.ic.flckr.feature.gallery.data

import com.ic.flckr.feature.gallery.data.entity.FlckrPhotoEntity
import com.ic.flckr.feature.gallery.domain.model.Photo
import javax.inject.Inject

class EntityMapper @Inject constructor() {

    fun mapPhoto(from: FlckrPhotoEntity): Photo {
        return Photo(
            from.id,
            from.secret,
            from.server,
            from.farm,
        )
    }
}