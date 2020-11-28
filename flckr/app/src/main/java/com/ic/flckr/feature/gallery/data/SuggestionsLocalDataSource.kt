package com.ic.flckr.feature.gallery.data

import com.ic.flckr.common.data.db.FlckrDatabase
import com.ic.flckr.feature.gallery.data.entity.SearchSuggestionEntity
import com.ic.flckr.utils.TimeProvider
import com.ic.logger.Logger
import javax.inject.Inject

class SuggestionsLocalDataSource @Inject constructor(
    private val database: FlckrDatabase,
    private val timeProvider: TimeProvider
) {

    suspend fun loadSuggestions(searchQuery: String?): List<String> {
        return database.getSearchSuggestionsDao().run {
            searchQuery?.let { getSuggestions(it) } ?: getHistoricalSuggestions()
        }
    }

    suspend fun saveSuggestions(searchQuery: String) {
        try {
            database.getSearchSuggestionsDao().insert(
                SearchSuggestionEntity(
                    searchQuery,
                    timeProvider.currentTimeMillis()
                )
            )
        } catch (e: Exception) {
            L.warn { "Failed to insert search query. Error: $e" }
        }
    }

    companion object {
        private val L = Logger()
    }
}