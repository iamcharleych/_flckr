package com.ic.flckr.feature.gallery.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.ic.flckr.feature.gallery.data.entity.SearchSuggestionEntity

@Dao
interface SearchSuggestionsDao : BaseEntityDao<SearchSuggestionEntity> {

    @Query("SELECT queryText FROM search_suggestions WHERE queryText LIKE '%'||:searchText||'%' LIMIT 8")
    suspend fun getSuggestions(searchText: String): List<String>

    @Query("SELECT queryText FROM search_suggestions ORDER BY timestamp DESC LIMIT 8")
    suspend fun getHistoricalSuggestions(): List<String>
}