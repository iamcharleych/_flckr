package com.ic.flckr.feature.gallery.ui

import androidx.lifecycle.*
import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.SearchUseCase
import com.ic.flckr.feature.gallery.domain.model.Photo
import com.ic.flckr.feature.gallery.ui.GalleryView.*
import com.ic.flckr.feature.gallery.ui.GalleryView.Event.SearchTriggered
import com.ic.flckr.feature.gallery.ui.model.LoadingState
import com.ic.flckr.feature.gallery.ui.model.ModelMapper
import com.ic.flckr.feature.gallery.ui.model.PhotoModelItem
import com.ic.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val modelMapper: ModelMapper
): ViewModel() {

    private var searchQuery: String? = null
    private var page: Int = 0

    val galleryObserver = Observer<Event> {
        L.debug { "galleryObserver: $it" }
        when (it) {
            is SearchTriggered -> handleSearchTriggered(it.searchQuery)
            is Event.LoadMoreRequested -> handleLoadMore()
        }
    }

    private val _loadingState = MutableLiveData<LoadingState>(LoadingState.Idle)
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _photoItems = MutableLiveData<List<PhotoModelItem>>()
    val photoItems: LiveData<List<PhotoModelItem>> = _photoItems

    init {
        handleSearchTriggered(null)
    }

    private fun handleSearchTriggered(searchQuery: String?) {
        viewModelScope.launch {
            page = 0
            this@GalleryViewModel.searchQuery = searchQuery
            _loadingState.value = LoadingState.Loading
            val result = withContext(Dispatchers.IO) {
                searchUseCase(searchQuery, page)
            }
            handleSearchResult(result)
        }
    }

    private fun handleLoadMore() {
        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading
            val result = withContext(Dispatchers.IO) {
                searchUseCase(searchQuery, ++page)
            }
            handleSearchResult(result)
        }
    }

    private suspend fun handleSearchResult(result: Result<List<Photo>>) {
        when (result) {
            is Result.Success -> {
                _loadingState.value = LoadingState.Completed
                _photoItems.value = result.data.map { modelMapper.mapItem(it) }
            }
            is Result.Fail -> {
                _loadingState.value = LoadingState.Failed
            }
        }

        delay(PRE_IDLE_DELAY)
        _loadingState.value = LoadingState.Idle
    }

    companion object {
        private val L = Logger()

        private const val PRE_IDLE_DELAY = 350L
    }
}