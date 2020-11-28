package com.ic.flckr.feature.gallery.domain.model

data class Photo(
    val id: Long,
    val secret: String,
    val server: String,
    val farm: Int
) {

    // https://www.flickr.com/services/api/misc.urls.html
    // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
    fun getImageUrl(size: PhotoSize): String {
        return "https://farm$farm.staticflickr.com/$server/${id}_${secret}_${size.value}.jpg"
    }
}
