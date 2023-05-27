package com.carefer.matches.ui.fragment.matchesList

import androidx.core.view.isVisible
import com.carefer.core.base.fragment.BaseUiHelper
import com.carefer.matches.databinding.FragmentMatchesBinding
import com.carefer.matches.domain.entity.local.Days
import com.carefer.matches.domain.entity.local.MatchItem
import javax.inject.Inject

class MatchesFragmentHelper @Inject constructor() : BaseUiHelper() {

    val daysList = listOf(Days.TODAY.name, Days.TOMORROW.name)
    fun handleShimmerLoading(
        show: Boolean,
        isShimmer: Boolean,
        binding: FragmentMatchesBinding
    ) {
        if (isShimmer) {
            binding.rvMatches.isVisible = !show
            binding.shimmerLayout.root.isVisible = show
            if (show) binding.shimmerLayout.root.startShimmerAnimation()
        }
    }

    fun handleScrollToTodayItem(
        binding: FragmentMatchesBinding,
        matchesList: List<MatchItem>,
    ) {
        binding.rvMatches.scrollToPosition(getPositionOfTodayItem(matchesList))
    }

    private fun getPositionOfTodayItem(matchesList: List<MatchItem>): Int {
        return matchesList.indexOfFirst { it.headerTitle in daysList }
    }

    fun setMatchesFavourite(
        matchesAdapter: MatchesAdapter,
        matchesList: List<MatchItem>,
        favMatches: List<String>?
    ) {
        val updatedList = matchesList.map { remoteMatch ->
            remoteMatch.copy(isFavorite = favMatches?.contains(remoteMatch.id.toString()) == true)
        }
        matchesAdapter.submitList(updatedList)
    }

    fun getFavMatches(matchesList: MutableList<MatchItem>): MutableList<MatchItem>? {
        return matchesList.filter { it.isFavorite }.toMutableList()
    }
}