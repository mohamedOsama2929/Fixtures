package com.carefer.matches.ui.fragment.matchesList

import androidx.core.view.isVisible
import com.carefer.core.base.fragment.BaseUiHelper
import com.carefer.matches.databinding.FragmentMatchesBinding
import com.carefer.matches.domain.entity.local.MatchItem
import javax.inject.Inject

class MatchesFragmentHelper @Inject constructor() : BaseUiHelper() {

    fun handleShimmerLoading(
        show: Boolean,
        isShimmer: Boolean,
        binding: FragmentMatchesBinding
    ) {
        if (isShimmer) {
            binding.rvFixtures.isVisible = !show
            binding.shimmerLayout.root.isVisible = show
            if (show) binding.shimmerLayout.root.startShimmerAnimation()
        }
    }

    fun handleScrollToTodayItem(
        binding: FragmentMatchesBinding,
        fixturesList: List<MatchItem>,
    ) {
        binding.rvFixtures.scrollToPosition(getPositionOfTodayItem(fixturesList))
    }

    private fun getPositionOfTodayItem(fixturesList: List<MatchItem>): Int {
        return fixturesList.indexOfFirst { it.headerTitle == "Today" || it.headerTitle == "Tomorrow" }

    }

    fun setMatchesFavourite(
        matchesAdapter: MatchesAdapter,
        fixturesList: List<MatchItem>,
        favMatches: List<String>?
    ) {
        fixturesList.forEach { remoteMatch ->
            remoteMatch.isFavorite = favMatches?.contains(remoteMatch.id.toString()) == true
        }

        matchesAdapter.submitList(fixturesList)


    }

    fun getFavMatches(matchesList: MutableList<MatchItem>): MutableList<MatchItem>? {
        return matchesList.filter { it.isFavorite }.toMutableList()
    }
}