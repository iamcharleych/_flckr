package com.ic.flckr.feature.gallery.data

import com.ic.flckr.common.domain.Result
import com.ic.flckr.feature.gallery.domain.SuggestionsRepository
import javax.inject.Inject

class SuggestionsRepositoryImpl @Inject constructor(
    private val suggestionsLocalDataSource: SuggestionsLocalDataSource
): SuggestionsRepository {
    override suspend fun loadSuggestions(query: String?): Result<List<String>> {
        return try {
            val res = suggestionsLocalDataSource.loadSuggestions(query)
            Result.Success(res)
        } catch (e: Exception) {
            Result.Fail("Failed to load suggestions for query: $query", e)
        }
    }
}