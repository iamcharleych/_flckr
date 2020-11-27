package com.ic.flckr.feature.gallery.ui.model

sealed class LoadingState {
    object Idle : LoadingState()
    object Loading : LoadingState()
    object Failed : LoadingState()
    object Completed : LoadingState()
}