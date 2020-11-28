package com.ic.flckr.widget.toast

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.ic.flckr.R

class FlckrToast {
    companion object {
        val GENERAL = GeneralToastDescriptor()
        val DONE = ImageToastDescriptor(R.drawable.ic_done)
        val FAIL = ImageToastDescriptor(R.drawable.ic_cancel_white)
    }

    abstract class BaseToastDescriptor(@LayoutRes val layoutRes: Int, @IdRes val textViewId: Int) {
        var toastGravity = Gravity.CENTER
        var horizontalOffset = 0
        var verticalOffset = 0

        fun show(context: Context, @StringRes textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
            show(context, context.getString(textRes), duration)
        }

        fun show(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) {
            with(Toast(context)) {
                view = prepareView(context, text)
                this.duration = duration
                setGravity(toastGravity, horizontalOffset, verticalOffset)

                show()
            }
        }

        private fun prepareView(context: Context, text: String): View {
            return LayoutInflater.from(context).inflate(layoutRes, null, false).apply {
                findViewById<TextView>(textViewId).text = text
                adjustContentView(this)
            }
        }

        abstract fun adjustContentView(contentView: View)
    }

    class GeneralToastDescriptor : BaseToastDescriptor(R.layout.toast_general, R.id.toastTextView) {
        override fun adjustContentView(contentView: View) {
            // no-op
        }
    }

    class ImageToastDescriptor(
        @DrawableRes private val iconRes: Int
    ) : BaseToastDescriptor(R.layout.toast_with_image, R.id.toastTextView) {
        override fun adjustContentView(contentView: View) {
            contentView.findViewById<ImageView>(R.id.toastIcon).setImageResource(iconRes)
        }
    }
}