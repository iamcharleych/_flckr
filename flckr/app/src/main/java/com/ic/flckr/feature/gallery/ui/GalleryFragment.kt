package com.ic.flckr.feature.gallery.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.ic.flckr.R
import com.ic.flckr.common.ui.viewBinding
import com.ic.flckr.databinding.FragmentGalleryBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: GalleryViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentGalleryBinding::bind)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}