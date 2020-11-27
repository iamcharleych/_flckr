package com.ic.flckr.feature.gallery.ui

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ic.flckr.R
import com.ic.flckr.common.di.glide.GlideRequest
import com.ic.flckr.databinding.FragmentGalleryBinding
import com.ic.flckr.feature.gallery.ui.model.LoadingState
import com.ic.flckr.feature.gallery.ui.model.PhotoItemModel

class GalleryView(
    private val binding: FragmentGalleryBinding,
    private val largeThumbRequest: GlideRequest<Drawable>,
    private val smallThumbRequest: GlideRequest<Drawable>
) {

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    private val adapter = GalleryAdapter(largeThumbRequest, smallThumbRequest).apply {
        galleryItemClickListener = { photoItemModel -> TODO() }
    }

    init {
        binding.gridView.run {
            val gridMargin = context.resources.getDimensionPixelSize(R.dimen.grid_item_margin)
            val columnsCount = context.resources.getInteger(R.integer.grid_columns_count)
            layoutManager = GridLayoutManager(context, columnsCount)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.set(gridMargin, gridMargin, gridMargin, gridMargin)
                }
            })
            adapter = this@GalleryView.adapter
        }
    }

    fun updateLoadingState(state: LoadingState) {

    }

    fun setItems(items: List<PhotoItemModel>) {
        adapter.submitList(items)
    }

    sealed class Event {
        data class SearchTriggered(val searchQuery: String?) : Event()
        object LoadMoreRequested : Event()
    }
}