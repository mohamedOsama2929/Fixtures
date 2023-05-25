package com.carefer.fixtures.ui.fragment.fixturesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.carefer.core.base.fragment.BaseFragment
import com.carefer.core.base.view_model.ApiState
import com.carefer.fixtures.R
import com.carefer.fixtures.databinding.FragmentFixturesBinding
import com.carefer.fixtures.domain.entity.local.MatchItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FixturesFragment :
    BaseFragment<FragmentFixturesBinding, FixturesViewModel, FixturesFragmentHelper>(
        FragmentFixturesBinding::inflate
    ) {

    override val viewModel: FixturesViewModel by activityViewModels()

    private val fixturesAdapter = FixturesAdapter(::onFavOnClicked)
    private var favMatchesList: MutableList<String> = mutableListOf()
    override fun showLoading(show: Boolean, isShimmer: Boolean) {
        super.showLoading(show, isShimmer)
        fragmentHelper.handleShimmerLoading(show, isShimmer, binding)

    }

    @Inject
    override lateinit var fragmentHelper: FixturesFragmentHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbarListener?.setActivityToolbarTitle(getString(R.string.fixtures))
        toolbarListener?.hideActivityToolbar()
        super.onViewCreated(view, savedInstanceState)
        viewModel.handelViewIntent()
        collectFixturesList()
        collectFavoriteMatches()
        viewModel.send(FixturesIntent.GetFavouredMatchesList)


    }

    private fun collectFavoriteMatches() {
        viewModel.getFavouredMatchesListStateFlow.asLiveData().observe(viewLifecycleOwner) {
            favMatchesList = it.toMutableList()
            viewModel.send(FixturesIntent.GetFixturesList("2021"))

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
        binding.rvFixtures.adapter = fixturesAdapter
        fragmentHelper.setMatchesFavourite(fixturesAdapter, fixturesList, favMatchesList)
        fragmentHelper.handleScrollToTodayItem(binding, fixturesList)
    }

    private fun onFavOnClicked(item: MatchItem) {
        if (item.isFavorite) {
            viewModel.send(FixturesIntent.SetMatchFavour(item.id.toString()))
        } else {
            viewModel.send(FixturesIntent.RemoveMatchFavour(item.id.toString()))
        }
    }

}