package com.ic.flckr.di.module

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import androidx.core.text.BidiFormatter
import com.ic.flckr.FlckrApp
import dagger.Module
import dagger.Provides

@Module
object AndroidModule {

    @Provides
    fun provideApplication(app: FlckrApp): Application = app

    @Provides
    fun provideContext(app: FlckrApp): Context = app.applicationContext

    @Provides
    fun provideContentResolver(context: Context) = context.contentResolver

    @Provides
    fun provideBidiFormatter(): BidiFormatter = BidiFormatter.getInstance()

    @Provides
    fun provideAssetManager(context: Context): AssetManager = context.assets
}