package com.majorik.moviebox.extensions

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import com.majorik.moviebox.utils.DebouncingQueryTextListener
import timber.log.Timber

fun SearchView.onQueryTextChange(lifecycle: Lifecycle, action: (String) -> Unit) {
    this.setOnQueryTextListener(
        DebouncingQueryTextListener(lifecycle) { newText ->
            newText.let {
                action.invoke(newText ?: "")
                Timber.i(newText)
            }
        }
    )
}
