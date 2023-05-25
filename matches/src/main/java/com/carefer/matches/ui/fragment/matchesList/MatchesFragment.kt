package com.carefer.matches.ui.fragment.matchesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.carefer.core.base.fragment.BaseFragment
import com.carefer.core.base.view_model.ApiState
import com.carefer.matches.R
import com.carefer.matches.databinding.FragmentMatchesBinding
import com.carefer.matches.domain.entity.local.MatchItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MatchesFragment :
    BaseFragment<FragmentMatchesBinding, MatchesViewModel, MatchesFragmentHelper>(
        FragmentMatchesBinding::inflate
    ) {

    override val viewModel: MatchesViewModel by activityViewModels()

    private val matchesAdapter = MatchesAdapter(::onFavOnClicked)
    private var favMatchesList: MutableList<String> = mutableListOf()
    override fun showLoading(show: Boolean, isShimmer: Boolean) {
        super.showLoading(show, isShimmer)
        fragmentHelper.handleShimmerLoading(show, isShimmer, binding)

    }

    @Inject
    override lateinit var fragmentHelper: MatchesFragmentHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarListener?.setActivityToolbarTitle(getString(R.string.matches))
        toolbarListener?.hideActivityToolbar()
        super.onViewCreated(view, savedInstanceState)
        viewModel.handelViewIntent()
        collectFixturesList()
        collectFavoriteMatches()
        viewModel.send(MatchesIntent.GetFavouredMatchesList)


    }

    private fun collectFavoriteMatches() {
        viewModel.getFavouredMatchesListStateFlow.asLiveData().observe(viewLifecycleOwner) {
            favMatchesList = it.toMutableList()
            viewModel.send(MatchesIntent.GetMatchesList("2021"))

        }

    }

    private fun collectFixturesList() {
        viewModel.getFixturesListStateFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Success -> {
                    setMatchesList(it.successData, favMatchesList)
                }

                else -> {}
            }
        }
    }

    private fun setMatchesList(fixturesList: List<MatchItem>, favMatchesList: MutableList<String>) {
        binding.rvFixtures.adapter = matchesAdapter
        fragmentHelper.setMatchesFavourite(matchesAdapter, fixturesList, favMatchesList)
        fragmentHelper.handleScrollToTodayItem(binding, fixturesList)
    }

    private fun onFavOnClicked(item: MatchItem) {
        if (item.isFavorite) {
            viewModel.send(MatchesIntent.SetMatchFavour(item.id.toString()))
        } else {
            viewModel.send(MatchesIntent.RemoveMatchFavour(item.id.toString()))
        }
    }

}