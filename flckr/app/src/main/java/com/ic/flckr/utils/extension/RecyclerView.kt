package com.ic.flckr.utils.extension

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.OnScrollListener.throttleOnScrolled(millis: Long): RecyclerView.OnScrollListener {
    return object : RecyclerView.OnScrollListener() {
        private var nextTime = 0L

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            this@throttleOnScrolled.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val curTime = System.currentTimeMillis()
            if (curTime >= nextTime) {
                nextTime = curTime + millis

                this@throttleOnScrolled.onScrolled(recyclerView, dx, dy)
            }
        }
    }
}