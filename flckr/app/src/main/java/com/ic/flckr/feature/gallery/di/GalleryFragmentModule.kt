package com.ic.flckr.feature.gallery.di

import androidx.lifecycle.ViewModel
import com.ic.flckr.common.di.ViewModelKey
import com.ic.flckr.feature.gallery.ui.GalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [GalleryModule::class])
abstract class GalleryFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun bindMediaGalleryViewModel(viewModel: GalleryViewModel): ViewModel
}