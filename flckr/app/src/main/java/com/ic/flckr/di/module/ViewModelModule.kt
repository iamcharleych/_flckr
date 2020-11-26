package com.ic.flckr.di.module

import androidx.lifecycle.ViewModelProvider
import com.ic.flckr.common.ui.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}