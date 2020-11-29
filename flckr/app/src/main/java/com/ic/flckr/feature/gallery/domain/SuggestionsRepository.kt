package com.ic.flckr.feature.gallery.domain

import com.ic.flckr.common.domain.Result

interface SuggestionsRepository {
    suspend fun loadSuggestions(query: String?): Result<List<String>>
}