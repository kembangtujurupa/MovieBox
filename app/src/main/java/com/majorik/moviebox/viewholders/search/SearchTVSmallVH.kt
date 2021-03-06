package com.majorik.moviebox.viewholders.search

import androidx.recyclerview.widget.RecyclerView
import com.majorik.domain.constants.UrlConstants
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.databinding.ItemMediumPosterCardBinding
import com.majorik.moviebox.extensions.displayImageWithCenterCrop
import kotlinx.android.synthetic.main.item_medium_poster_card.view.*

class SearchTVSmallVH(val parent: ItemMediumPosterCardBinding) :
    RecyclerView.ViewHolder(parent.root) {
    @UseExperimental(ExperimentalStdlibApi::class)
    fun bindTo(item: TV?) {
        item?.let {
            parent.collectionImage.displayImageWithCenterCrop(UrlConstants.TMDB_POSTER_SIZE_185 + it.posterPath)
            parent.title.text = it.name
        }
    }
}
