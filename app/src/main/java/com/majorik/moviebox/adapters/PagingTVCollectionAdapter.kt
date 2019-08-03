package com.majorik.moviebox.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.majorik.domain.NetworkState
import com.majorik.domain.models.tv.TVResponse
import com.majorik.moviebox.R
import com.majorik.moviebox.ui.tvDetails.TVDetailsActivity
import com.majorik.moviebox.viewholders.NetworkStateViewHolder
import com.majorik.moviebox.viewholders.TVPagedItemVH

class PagingTVCollectionAdapter(private val callback: OnClickListener) :
    PagedListAdapter<TVResponse.TV, ViewHolder>(
        diffCallback
    ) {

    private var networkState: NetworkState? = null

    interface OnClickListener {
        fun onClickRetry()
        fun whenListIsUpdated(size: Int, networkState: NetworkState?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_small_poster_card -> TVPagedItemVH(
                view
            )
            R.layout.item_network_state -> NetworkStateViewHolder(
                view
            )
            else -> throw IllegalArgumentException("Неизвестный тип view: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_small_poster_card -> {
                (holder as TVPagedItemVH).bindTo(getItem(position))

                holder.itemView.setOnClickListener {
                    getItem(position)?.let { movie ->
                        val intent = Intent(holder.parent.context, TVDetailsActivity::class.java)

                        intent.putExtra("id", movie.id)

                        holder.parent.context.startActivity(intent)
                    }
                }
            }
            R.layout.item_network_state -> {
                (holder as NetworkStateViewHolder).bindTo(
                    networkState,
                    callback
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.item_small_poster_card
        }
    }

    override fun getItemCount(): Int {
        this.callback.whenListIsUpdated(super.getItemCount(), this.networkState)
        return super.getItemCount()
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS

    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val diffCallback =
            object : DiffUtil.ItemCallback<TVResponse.TV>() {
                override fun areItemsTheSame(
                    oldItem: TVResponse.TV,
                    newItem: TVResponse.TV
                ): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: TVResponse.TV,
                    newItem: TVResponse.TV
                ): Boolean =
                    oldItem == newItem
            }
    }
}