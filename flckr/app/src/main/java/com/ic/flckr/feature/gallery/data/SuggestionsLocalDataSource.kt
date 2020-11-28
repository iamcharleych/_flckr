package com.ic.flckr.feature.gallery.data

import com.ic.flckr.common.data.db.FlckrDatabase
import javax.inject.Inject

class SuggestionsLocalDataSource @Inject constructor(
    private val database: FlckrDatabase
) {

    suspend fun loadSuggestions(searchQuery: String?): List<String> {
        return database.getSearchSuggestionsDao().run {
            searchQuery?.let { getSuggestions(it) } ?: getHistoricalSuggestions()
        }
    }
}