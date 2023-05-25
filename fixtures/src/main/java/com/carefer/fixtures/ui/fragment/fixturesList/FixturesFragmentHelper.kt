package com.carefer.fixtures.ui.fragment.fixturesList

import androidx.core.view.isVisible
import com.carefer.core.base.fragment.BaseUiHelper
import com.carefer.fixtures.databinding.FragmentFixturesBinding
import com.carefer.fixtures.domain.entity.local.MatchItem
import javax.inject.Inject

class FixturesFragmentHelper @Inject constructor() : BaseUiHelper() {
    /*internal fun toggleViews(
        isEmptyView: Boolean,
        context: Context,
        binding: FragmentFixturesBinding
    ) {
        when (isEmptyView) {
            true -> {
                binding.run {
                    pagingView.run {
                        rvPaging.isVisible = false
                        emptyView.isVisible = true
                    }
                }
            }

            false -> {
                binding.run {
                    pagingView.run {
                        rvPaging.isVisible = true
                        emptyView.isVisible = false
                    }
                }
            }
        }
        binding.pagingView.root.isVisible = true
    }*/

    /*  private fun toggleEmptyState(
     *//*     announcementsListQuery: AnnouncementsListQuery,
        announcementsFavListQuery: AnnouncementsListQuery,*//*
        context: Context,
        binding: FragmentFixturesBinding
    ) {

      //  setFavEmptyList(announcementsFavListQuery, context, binding)
        binding.pagingView.root.isVisible = true
    }

    private fun setEmptySearchList(binding: FragmentFixturesBinding, context: Context) {
        binding.run {
            pagingView.apply {
                tvEmptyList.text = context.getString(R.string.no_search_result)
            }
        }
    }*/

    private fun setFavEmptyList(
        //  announcementsFavListQuery: AnnouncementsListQuery, context: Context,
        binding: FragmentFixturesBinding
    ) {
        /*  if ((announcementsFavListQuery.filter?.message?.isNotEmpty() == true)) {
              setEmptySearchList(binding, context)

          } else {
              setEmptyList(binding, context)
          }*/
    }

    /*    private fun setEmptyList(
            binding: FragmentFixturesBinding,
            context: Context,
        ) {
            binding.run {
                pagingView.apply {
                    imgEmptyState.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.announcements_list_empty
                        )
                    )
                    tvEmptyList.text = context.getString(R.string.there_s_no_announcements_yet)
                }
            }
        }*/


    /*    internal fun setAnnouncementDetailsQuery(
            announcementId: String,
            isLastTabSelected: Boolean,
        ): AnnouncementDetailsQuery {
            val announcementDetailsQuery = AnnouncementDetailsQuery()
            when (isLastTabSelected) {
                true -> {
                    announcementDetailsQuery.apply {
                        this.announcementId = announcementId
                    }
                }

                false -> {
                    announcementDetailsQuery.apply {
                        this.announcementId = announcementId

                    }
                }
            }
            return announcementDetailsQuery
        }*/

    fun handleShimmerLoading(
        show: Boolean,
        isShimmer: Boolean,
        binding: FragmentFixturesBinding
    ) {
        if (isShimmer) {
            binding.rvFixtures.isVisible = !show
            binding.shimmerLayout.root.isVisible = show
            if (show) binding.shimmerLayout.root.startShimmerAnimation()
        }
    }

    fun handleScrollToTodayItem(
        binding: FragmentFixturesBinding,
        fixturesList: List<MatchItem>,
    ) {
        binding.rvFixtures.scrollToPosition(getPositionOfTodayItem(fixturesList))
    }

    private fun getPositionOfTodayItem(fixturesList: List<MatchItem>): Int {
        return fixturesList.indexOfFirst { it.headerTitle == "Today" || it.headerTitle == "Tomorrow" }

    }

    fun setMatchesFavourite(
        fixturesAdapter: FixturesAdapter,
        fixturesList: List<MatchItem>,
        favMatches: List<String>?
    ) {
        fixturesList.forEach { remoteMatch ->
            remoteMatch.isFavorite = favMatches?.contains(remoteMatch.id.toString()) == true
        }

        fixturesAdapter.submitList(fixturesList)


    }
}