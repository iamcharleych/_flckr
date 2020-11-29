package com.ic.flckr.common.di

import com.ic.flckr.FlckrApp
import com.ic.flckr.common.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(app: FlckrApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: FlckrApp): Builder

        fun build(): AppComponent
    }
}