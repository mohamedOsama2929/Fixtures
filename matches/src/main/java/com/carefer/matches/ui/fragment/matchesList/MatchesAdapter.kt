package com.carefer.matches.ui.fragment.matchesList

import android.content.Context
import android.widget.TextView
import androidx.core.view.isVisible
import com.carefer.core.base.adapter.diffutilsAdapter.BaseRecyclerAdapter
import com.carefer.matches.R
import com.carefer.matches.databinding.ItemMatchBinding
import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.entity.local.MatchStatus


class MatchesAdapter(
    private var onFavoriteImageClickListener: (MatchItem) -> Unit,

    ) :
    BaseRecyclerAdapter<ItemMatchBinding, MatchItem>(
        ItemMatchBinding::inflate, { oldItem, newItem -> oldItem.id == newItem.id }) {


    override fun bind(
        context: Context,
        binding: ItemMatchBinding,
        item: MatchItem,
        position: Int
    ) {
        binding.run {
            tvHomeTeamName.text = item.homeTeam
            tvAwayTeamName.text = item.awayTeam
            tvScore.text = item.score
            tvTime.text = item.matchTime
            tvScore.isVisible = item.matchStatus == MatchStatus.FINISHED
            tvTime.isVisible = item.matchStatus == MatchStatus.SCHEDULED

            if (item.isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_fav_transparent)
                ivFavorite.setColorFilter(android.R.color.transparent)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite)
                ivFavorite.colorFilter = null
            }

            ivFavorite.setOnClickListener {
                item.isFavorite = item.isFavorite.not()
                onFavoriteImageClickListener(item)
                notifyItemChanged(position)
            }

            if (position == 0) {
                showStickyHeaderTitle(item.headerTitle, tvStickyHeader)
            } else if ((item.headerTitle != currentList[position - 1].headerTitle)) {
                showStickyHeaderTitle(item.headerTitle, tvStickyHeader)
            } else {
                tvStickyHeader.isVisible = false
            }
        }
    }

    private fun showStickyHeaderTitle(
        headerTitle: String,
        tvHeader: TextView
    ) {
        tvHeader.text = headerTitle
        tvHeader.isVisible = true
    }
}
