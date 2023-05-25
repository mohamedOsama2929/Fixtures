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
        viewModel.send(FixturesIntent.GetFixturesList("2021"))

    }

    private fun collectFixturesList() {
        viewModel.getFixturesListStateFlow.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is ApiState.Success -> {
                    setFixturesList(it.successData)
                }

                else -> {}
            }
        }
    }

    private fun setFixturesList(fixturesList: List<MatchItem>) {
        val fixturesAdapter = FixturesAdapter()
        binding.rvFixtures.adapter = fixturesAdapter
        fixturesAdapter.submitList(fixturesList)
        fragmentHelper.handleScrollToTodayItem(binding, fixturesList)
    }

}