package com.ic.flckr.common.di.module

import com.ic.flckr.utils.Reachability
import com.ic.flckr.utils.SystemTimeProvider
import com.ic.flckr.utils.TimeProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UtilsModule {

    @Provides
    @Singleton
    fun provideReachability(): Reachability = Reachability

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider = SystemTimeProvider
}