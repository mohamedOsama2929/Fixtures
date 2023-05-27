package com.carefer.matches.ui.fragment.matchesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.carefer.core.base.fragment.BaseFragment
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

    override val viewModel: MatchesViewModel by viewModels()

    private val matchesAdapter = MatchesAdapter(::onFavOnClicked)
    private var favMatchesList: MutableList<String> = mutableListOf()
    private var matchesList: MutableList<MatchItem> = mutableListOf()

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
        collectMatchesList()
        collectFavoriteMatches()
        viewModel.send(MatchesIntent.GetFavouredMatchesList)
        initViews()
    }

    private fun initViews() {
        binding.switchUnReadOnly.setOnCheckedChangeListener { _, isChecked ->

            val matchesList = if (isChecked) {
                fragmentHelper.getFavMatches(matchesList)
            } else {
                matchesList
            }
            matchesAdapter.apply {
                clear()
                submitList(matchesList)
                matchesList?.toList()?.let { fragmentHelper.handleScrollToTodayItem(binding, it) }
            }
        }
    }

    private fun collectFavoriteMatches() {
        viewModel.getFavouredMatchesListStateFlow.asLiveData().observe(viewLifecycleOwner) {
            favMatchesList = it.toMutableList()
            viewModel.send(MatchesIntent.GetMatchesList("2021"))

        }

    }

    private fun collectMatchesList() {
        viewModel.getMatchesListStateFlow.asLiveData().observe(viewLifecycleOwner) {
            setMatchesList(it, favMatchesList)
            matchesList = it.toMutableList()


        }
    }

    private fun setMatchesList(matchesList: List<MatchItem>, favMatchesList: MutableList<String>) {
        binding.rvMatches.adapter = matchesAdapter
        fragmentHelper.setMatchesFavourite(matchesAdapter, matchesList, favMatchesList)
        fragmentHelper.handleScrollToTodayItem(binding, matchesList)
    }

    private fun onFavOnClicked(item: MatchItem) {
        if (item.isFavorite) {
            viewModel.send(MatchesIntent.SetMatchFavour(item.id.toString()))
        } else {
            viewModel.send(MatchesIntent.RemoveMatchFavour(item.id.toString()))
        }
    }

}