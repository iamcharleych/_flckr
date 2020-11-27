package com.ic.flckr.feature.gallery.ui

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
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
) : SearchView.OnQueryTextListener {

    private val _events = MutableLiveData<Event>()
    val events: LiveData<Event> = _events

    private val adapter = GalleryAdapter(largeThumbRequest, smallThumbRequest).apply {
        galleryItemClickListener = { photoItemModel -> TODO() }
    }

    private val suggestionsAdapter = SimpleCursorAdapter(
        binding.root.context,
        android.R.layout.simple_list_item_1,
        null,
        arrayOf("suggestion"),
        intArrayOf(android.R.id.text1),
        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
    )

    private var menuItem: MenuItem? = null

    init {
        binding.run {
            gridView.run {
                val gridMargin = context.resources.getDimensionPixelSize(R.dimen.grid_item_margin)
                val columnsCount = context.resources.getInteger(R.integer.grid_columns_count)
                layoutManager = GridLayoutManager(context, columnsCount)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.set(gridMargin, gridMargin, gridMargin, gridMargin)
                    }
                })
                adapter = this@GalleryView.adapter
            }
            toolbar.run {
                inflateMenu(R.menu.menu_photo_list)

                menuItem = menu.findItem(R.id.action_search)
                val searchView = menuItem?.actionView as? SearchView
                searchView?.let { setupSearchView(it) }
            }
        }
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.apply {
            queryHint = searchView.context.getString(R.string.search)
            suggestionsAdapter = this@GalleryView.suggestionsAdapter
            setOnQueryTextListener(this@GalleryView)
            setOnSuggestionListener(object : SearchView.OnSuggestionListener {
                override fun onSuggestionSelect(position: Int): Boolean {
                    return false
                }

                override fun onSuggestionClick(position: Int): Boolean {
                    val query = suggestionsAdapter.cursor.getString(1)
                    menuItem?.collapseActionView()
                    _events.value = Event.SearchTriggered(query)
                    return false
                }

            })
            // TODO: apply workaround. Temporary solution due to lack of time
            findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)?.threshold = 0
        }
    }

    fun updateLoadingState(state: LoadingState) {

    }

    fun setItems(items: List<PhotoItemModel>) {
        adapter.submitList(items)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        menuItem?.collapseActionView()
        _events.value = Event.SearchTriggered(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        _events.value = Event.SuggestionsRequested(newText)
        return true
    }

    sealed class Event {
        data class SearchTriggered(val searchQuery: String?) : Event()
        object LoadMoreRequested : Event()
        data class SuggestionsRequested(val searchQuery: String?) : Event()
    }
}