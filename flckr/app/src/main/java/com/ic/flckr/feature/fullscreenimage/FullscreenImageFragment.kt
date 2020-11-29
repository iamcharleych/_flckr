package com.ic.flckr.feature.fullscreenimage

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.ic.flckr.R
import com.ic.flckr.common.di.glide.GlideApp
import com.ic.flckr.common.ui.viewBinding
import com.ic.flckr.databinding.FragmentFullscreenImageBinding

class FullscreenImageFragment : Fragment(R.layout.fragment_fullscreen_image) {

    private val binding by viewBinding(FragmentFullscreenImageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = requireArguments().getString(ARG_IMG_URL)
        if (!url.isNullOrEmpty()) {
            GlideApp.with(binding.root.context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(binding.fullscreenImage)
        }

        view.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    companion object {
        private const val ARG_IMG_URL = "arg_image_url"

        fun newInstance(imgUrl: String): FullscreenImageFragment {
            return FullscreenImageFragment().apply {
                arguments = bundleOf(ARG_IMG_URL to imgUrl)
            }
        }
    }
}