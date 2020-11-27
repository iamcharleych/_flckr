package com.ic.flckr.feature.gallery.ui.model

import com.ic.flckr.feature.gallery.domain.model.Photo
import javax.inject.Inject

class ModelMapper @Inject constructor() {

    fun mapItem(from: Photo): PhotoModelItem {
        return PhotoModelItem()
    }
}