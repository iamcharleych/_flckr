package com.ic.flckr.feature.gallery.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ic.flckr.common.di.glide.GlideRequest
import com.ic.flckr.common.ui.ViewBindingViewHolder
import com.ic.flckr.databinding.ItemGalleryPhotoBinding
import com.ic.flckr.feature.gallery.ui.model.PhotoItemModel
import com.ic.flckr.feature.gallery.ui.model.PhotoItemModelDiff

class GalleryAdapter(
    private val largeThumbRequest: GlideRequest<Drawable>,
    private val smallThumbRequest: GlideRequest<Drawable>
) : ListAdapter<PhotoItemModel, ViewBindingViewHolder<PhotoItemModel>>(PhotoItemModelDiff) {

    var galleryItemClickListener: ((PhotoItemModel) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewBindingViewHolder<PhotoItemModel> {
        val inflater = LayoutInflater.from(parent.context)

        return ItemGalleryPhotoBinding.inflate(inflater, parent, false).let(::GalleryItemViewHolder)
    }

    override fun onBindViewHolder(holder: ViewBindingViewHolder<PhotoItemModel>, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GalleryItemViewHolder(
        private val binding: ItemGalleryPhotoBinding
    ) : ViewBindingViewHolder<PhotoItemModel>(binding) {
        private var item: PhotoItemModel? = null

        init {
            binding.root.setOnClickListener {
                item?.let { media ->
                    galleryItemClickListener?.invoke(media)
                }
            }
        }

        override fun bind(model: PhotoItemModel) {
            this.item = model

            largeThumbRequest
                .load(model.largeThumbUrl)
                .thumbnail(smallThumbRequest.load(model.smallThumbUrl))
                .into(binding.image)
        }
    }
}