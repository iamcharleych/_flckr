package com.ic.flckr.feature.gallery.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_suggestions")
data class SearchSuggestionEntity(@PrimaryKey var queryText: String, var timestamp: Long)