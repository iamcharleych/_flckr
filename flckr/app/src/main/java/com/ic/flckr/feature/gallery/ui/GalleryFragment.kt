package com.ic.flckr.feature.gallery.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ic.flckr.R
import com.ic.flckr.common.di.glide.GlideRequest
import com.ic.flckr.common.ui.viewBinding
import com.ic.flckr.databinding.FragmentGalleryBinding
import com.ic.flckr.feature.fullscreenimage.FullscreenImageFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Named

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    @Inject
    @Named("largeThumbRequest")
    lateinit var largeThumbRequest: GlideRequest<Drawable>
    @Inject
    @Named("smallThumbRequest")
    lateinit var smallThumbRequest: GlideRequest<Drawable>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: GalleryViewModel by viewModels { viewModelFactory }
    private lateinit var galleryView: GalleryView

    private val binding by viewBinding(FragmentGalleryBinding::bind)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        galleryView = GalleryView(
            binding,
            largeThumbRequest,
            smallThumbRequest
        ).also {
            it.events.observe(viewLifecycleOwner, viewModel.galleryObserver)
            viewModel.run {
                loadingState.observe(viewLifecycleOwner, it::updateLoadingState)
                photoItems.observe(viewLifecycleOwner, it::setItems)
                suggestionsCursorData.observe(viewLifecycleOwner, it::updateSuggestions)
            }
        }

        viewModel.navigationEvents.observe(viewLifecycleOwner) {
            when (it) {
                is NavigationEvent.OpenImage -> openFragment(
                    FullscreenImageFragment.newInstance(it.url),
                    "FullscreenImageFragment"
                )
            }
        }
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        parentFragmentManager.commit {
            setCustomAnimations(R.anim.pop_in, 0, 0, 0)
            add(R.id.fragmentContainerView, fragment)
            addToBackStack(tag)
        }
    }
}