package com.ic.flckr.feature.gallery.ui.model

import com.ic.flckr.feature.gallery.domain.model.Photo
import com.ic.flckr.feature.gallery.domain.model.PhotoSize
import javax.inject.Inject

class ModelMapper @Inject constructor() {

    fun mapItem(from: Photo): PhotoItemModel {
        return PhotoItemModel(
            from.id,
            from.getImageUrl(PhotoSize.SMALL),
            from.getImageUrl(PhotoSize.MEDIUM),
            from.getImageUrl(PhotoSize.LARGE),
        )
    }
}