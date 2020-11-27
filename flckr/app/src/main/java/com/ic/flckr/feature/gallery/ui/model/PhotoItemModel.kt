package com.ic.flckr.feature.gallery.ui.model

import androidx.recyclerview.widget.DiffUtil

data class PhotoItemModel(
    val id: Long,
    val smallThumbUrl: String,
    val largeThumbUrl: String,
    val photoUrl: String
)

object PhotoItemModelDiff : DiffUtil.ItemCallback<PhotoItemModel>() {

    override fun areItemsTheSame(oldItem: PhotoItemModel, newItem: PhotoItemModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItemModel, newItem: PhotoItemModel): Boolean {
        return oldItem == newItem
    }
}