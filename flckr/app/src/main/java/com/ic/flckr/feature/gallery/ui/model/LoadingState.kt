package com.ic.flckr.feature.gallery.ui.model

sealed class LoadingState {
    object Idle : LoadingState()
    object Loading : LoadingState()
    data class Failed(val message: String) : LoadingState()
    object Completed : LoadingState()
}