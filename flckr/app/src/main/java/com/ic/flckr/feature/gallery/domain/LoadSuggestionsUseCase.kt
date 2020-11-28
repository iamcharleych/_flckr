package com.ic.flckr.feature.gallery.domain

import com.ic.flckr.common.domain.Result
import javax.inject.Inject

class LoadSuggestionsUseCase @Inject constructor(
    private val suggestionsRepository: SuggestionsRepository
) {
    suspend operator fun invoke(query: String?): Result<List<String>> {
        return suggestionsRepository.loadSuggestions(query)
    }
}