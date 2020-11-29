package com.ic.flckr.feature.gallery.di

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ic.flckr.R
import com.ic.flckr.common.di.ViewModelKey
import com.ic.flckr.common.di.glide.GlideApp
import com.ic.flckr.common.di.glide.GlideRequest
import com.ic.flckr.common.di.scope.FragmentScope
import com.ic.flckr.feature.gallery.ui.GalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module(includes = [GalleryModule::class])
abstract class GalleryFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    abstract fun bindMediaGalleryViewModel(viewModel: GalleryViewModel): ViewModel

    companion object {
        @Provides
        @Named("smallThumbRequest")
        fun provideSmallThumbGlideRequest(context: Context): GlideRequest<Drawable> {
            return GlideApp.with(context.applicationContext)
                .asDrawable()
                .placeholder(R.drawable.placeholder)
                .centerCrop()
        }

        @Provides
        @Named("largeThumbRequest")
        fun provideLargeThumbGlideRequest(context: Context): GlideRequest<Drawable> {
            return GlideApp.with(context.applicationContext)
                .asDrawable()
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
        }
    }
}