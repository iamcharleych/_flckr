package com.ic.flckr.feature.gallery.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ic.flckr.databinding.FragmentGalleryBinding
import com.ic.flckr.feature.gallery.ui.model.LoadingState
import com.ic.flckr.feature.gallery.ui.model.PhotoModelItem

class GalleryView(
    private val binding: FragmentGalleryBinding
) {

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    init {

    }

    fun updateLoadingState(state: LoadingState) {

    }

    fun setItems(items: List<PhotoModelItem>) {

    }

    sealed class Event {
        data class SearchTriggered(val searchQuery: String?) : Event()
        object LoadMoreRequested : Event()
    }
}