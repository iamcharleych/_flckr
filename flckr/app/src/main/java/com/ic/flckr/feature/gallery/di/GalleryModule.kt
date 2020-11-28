package com.ic.flckr.feature.gallery.di

import com.ic.flckr.common.data.network.core.DataResponse
import com.ic.flckr.common.data.network.core.RestClient
import com.ic.flckr.common.di.scope.FragmentScope
import com.ic.flckr.feature.gallery.data.PhotoRepositoryImpl
import com.ic.flckr.feature.gallery.data.SuggestionsRepositoryImpl
import com.ic.flckr.feature.gallery.data.networkclient.RetrofitRestClient
import com.ic.flckr.feature.gallery.domain.PhotoRepository
import com.ic.flckr.feature.gallery.domain.SuggestionsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class GalleryModule {

    @Binds
    abstract fun bindRestClient(impl: RetrofitRestClient): RestClient<DataResponse<*>>

    @Binds
    abstract fun bindMediaRepository(impl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    abstract fun bindSuggestionsRepository(impl: SuggestionsRepositoryImpl): SuggestionsRepository

    companion object {
        @Provides
        @FragmentScope
        fun provideRetrofitRestClient(retrofit: Retrofit): RetrofitRestClient {
            return RetrofitRestClient(retrofit)
        }
    }
}