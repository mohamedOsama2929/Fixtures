package com.carefer.fixtures.ui.fragment.fixturesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.carefer.core.base.view_model.ApiState
import com.carefer.core.base.view_model.BaseViewModel
import com.carefer.fixtures.domain.entity.local.MatchItem
import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.usecase.GetFixturesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FixturesViewModel @Inject constructor(
    private val getFixturesListUseCase: GetFixturesListUseCase
) : BaseViewModel() {

    private val fixturesListIntent = MutableLiveData<FixturesIntent>()

    private val fixturesListObserver = Observer<FixturesIntent> {
        when (it) {

            is FixturesIntent.GetFixturesList -> {
                getFixturesList(it.id)
            }

        }
    }

    internal fun send(intentType: FixturesIntent) {
        fixturesListIntent.value = intentType
    }

    override fun handelViewIntent() {
        viewModelScope.launch {
            fixturesListIntent.observeForever(fixturesListObserver)
        }
    }

    private val _getFixturesListStateFlow =
        MutableStateFlow<ApiState<List<MatchItem>>>(ApiState.Idle)
    val getFixturesListStateFlow: StateFlow<ApiState<List<MatchItem>>>
        get() = _getFixturesListStateFlow

    private fun getFixturesList(id: String) {
        callApiWithApiState(_getFixturesListStateFlow, isShimmer = true) {
            getFixturesListUseCase.executeApiState(
                FixturesListQuery(id),
                it
            )
        }
    }

    override fun reset() {
        super.reset()
        fixturesListIntent.removeObserver(fixturesListObserver)
    }
}