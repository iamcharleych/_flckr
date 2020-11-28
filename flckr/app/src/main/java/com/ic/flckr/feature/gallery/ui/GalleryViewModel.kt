package com.ic.flckr.feature.gallery.ui

import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import androidx.lifecycle.*
import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.LoadSuggestionsUseCase
import com.ic.flckr.feature.gallery.domain.SearchUseCase
import com.ic.flckr.feature.gallery.domain.model.Photo
import com.ic.flckr.feature.gallery.ui.GalleryView.*
import com.ic.flckr.feature.gallery.ui.GalleryView.Event.*
import com.ic.flckr.feature.gallery.ui.model.LoadingState
import com.ic.flckr.feature.gallery.ui.model.ModelMapper
import com.ic.flckr.feature.gallery.ui.model.PhotoItemModel
import com.ic.logger.Logger
import kotlinx.coroutines.*
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val loadSuggestionsUseCase: LoadSuggestionsUseCase,
    private val modelMapper: ModelMapper
): ViewModel() {

    private var searchQuery: String? = null
    private var page: Int = 0

    val galleryObserver = Observer<Event> {
        L.debug { "galleryObserver: $it" }
        when (it) {
            is SearchTriggered -> handleSearchTriggered(it.searchQuery)
            is LoadMoreRequested -> handleLoadMore()
            is SuggestionsRequested -> handleSuggestionsRequest(it.searchQuery)
        }
    }

    private val _loadingState = MutableLiveData<LoadingState>(LoadingState.Idle)
    val loadingState: LiveData<LoadingState> = _loadingState

    private val _photoItems = MutableLiveData<List<PhotoItemModel>>()
    val photoItems: LiveData<List<PhotoItemModel>> = _photoItems

    private val _suggestionsCursorData = MutableLiveData<Cursor>()
    val suggestionsCursorData: LiveData<Cursor> = _suggestionsCursorData

    private var getSuggestionsJob: Job? = null

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
        if (_loadingState.value == LoadingState.Loading) {
            return // early exit
        }

        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading
            val result = withContext(Dispatchers.IO) {
                searchUseCase(searchQuery, ++page)
            }
            handleSearchResult(result)
        }
    }

    private fun handleSuggestionsRequest(searchQuery: String?) {
        getSuggestionsJob?.cancel()
        getSuggestionsJob = viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                loadSuggestionsUseCase(searchQuery)
            }
            L.debug { "handleSuggestionsRequest(): result = $result" }
            when (result) {
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        val cursor = MatrixCursor(arrayOf(BaseColumns._ID, "suggestion"))
                        result.data.forEachIndexed { index, s -> cursor.addRow(arrayOf(index, s)) }
                        _suggestionsCursorData.value = cursor
                    }
                }
                is Result.Fail -> {}
            }
        }.also {
            it.invokeOnCompletion { getSuggestionsJob = null }
        }
    }

    private suspend fun handleSearchResult(result: Result<List<Photo>>) {
        L.debug { "handleSearchResult(): result=$result" }
        when (result) {
            is Result.Success -> {
                _loadingState.value = LoadingState.Completed
                _photoItems.value = result.data.map { modelMapper.mapItem(it) }
            }
            is Result.Fail -> {
                _loadingState.value = LoadingState.Failed(result.message)
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