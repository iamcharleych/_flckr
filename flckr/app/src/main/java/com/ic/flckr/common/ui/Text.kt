package com.ic.flckr.common.ui

import android.content.Context
import android.text.Html
import androidx.annotation.StringRes

/**
 * This is an abstraction over a string that can be obtained from different sources.
 * Use it in your [androidx.lifecycle.ViewModel] implementation in order to remove dependency on a context.
 *
 * See also: [Locale changes and the AndroidViewModel antipattern](https://medium.com/androiddevelopers/locale-changes-and-the-androidviewmodel-antipattern-84eb677660d9)
 */
sealed class Text {
    data class Plain(val text: String) : Text()
    class Resource(@StringRes val resId: Int, vararg val formatArgs: Any) : Text()
    class Composite(vararg val texts: Text, val separator: String = "") : Text()
    object Empty : Text()

    fun getString(context: Context): String {
        return when (this) {
            is Plain -> text
            is Resource -> context.getString(resId, *formatArgs)
            is Composite -> texts.joinToString(separator) { it.getString(context) }
            is Empty -> ""
        }
    }

    fun getText(context: Context): CharSequence {
        return when (this) {
            is Plain -> text
            is Resource -> {
                when {
                    formatArgs.isEmpty() -> context.getText(resId)
                    android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N -> {
                        Html.fromHtml(String.format(getString(context), *formatArgs), 0)
                    }
                    else -> {
                        Html.fromHtml(String.format(getString(context), *formatArgs))
                    }
                }
            }
            is Composite -> texts.joinToString(separator) { it.getText(context) }
            is Empty -> ""
        }
    }

    fun isEmpty() = this is Empty
}