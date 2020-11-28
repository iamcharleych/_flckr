package com.ic.flckr.feature.gallery.ui

sealed class NavigationEvent {
    data class OpenImage(val url: String) : NavigationEvent()
}