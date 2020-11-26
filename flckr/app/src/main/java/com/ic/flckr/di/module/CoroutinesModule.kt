package com.ic.flckr.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.plus
import javax.inject.Singleton

@Module
object CoroutinesModule {

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return MainScope() + CoroutineName("AppScope")
    }
}