package com.ic.flckr.feature.gallery.data.entity

import com.google.gson.annotations.SerializedName

class FlckrPhotoCollection {
    var page: Int = 1
    @SerializedName("pages")
    var pagesCount: Int = 1
    @SerializedName("perpage")
    var photosPerPageCount: Int = 100
    @SerializedName("total")
    var totalCount: Int = 0
    var photo: List<FlckrPhotoEntity>? = null
}