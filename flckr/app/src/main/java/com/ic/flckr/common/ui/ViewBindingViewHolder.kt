package com.ic.flckr.common.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ViewBindingViewHolder<in T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: T)
}