package com.ic.flckr.di.module

import dagger.Module

@Module(
    includes = [
        AndroidModule::class,
        CoroutinesModule::class,
        NetworkModule::class,
        ViewModelModule::class,
    ]
)
abstract class AppModule {
}
