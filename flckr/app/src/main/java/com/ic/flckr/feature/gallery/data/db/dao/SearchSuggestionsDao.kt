package com.ic.flckr.feature.gallery.data.db.dao

//@Dao
//interface SearchSuggestionsDao : BaseEntityDao<SearchSuggestionEntity> {
//
//    @Query("SELECT queryText FROM search_suggestions WHERE queryText LIKE '%'||:searchText||'%' LIMIT 8")
//    fun getSuggestions(searchText: String): Single<List<String>>
//
//    @Query("SELECT queryText FROM search_suggestions ORDER BY timestamp DESC LIMIT 8")
//    fun getHistoricalSuggestions(): Single<List<String>>
//}