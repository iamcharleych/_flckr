package com.ic.flckr.common.di.module

import com.ic.flckr.MainActivity
import com.ic.flckr.common.di.module.activity.MainActivityModule
import com.ic.flckr.common.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        AndroidModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        UtilsModule::class
    ]
)
abstract class AppModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
}
