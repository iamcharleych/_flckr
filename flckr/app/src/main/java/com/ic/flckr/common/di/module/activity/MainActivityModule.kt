package com.ic.flckr.common.di.module.activity

import com.ic.flckr.common.di.scope.FragmentScope
import com.ic.flckr.feature.gallery.di.GalleryFragmentModule
import com.ic.flckr.feature.gallery.di.GalleryModule
import com.ic.flckr.feature.gallery.ui.GalleryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [GalleryFragmentModule::class])
    abstract fun contributeGalleryFragment(): GalleryFragment
}