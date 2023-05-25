package com.carefer.fixtures.ui.fragment.fixturesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.carefer.core.base.view_model.ApiState
import com.carefer.core.base.view_model.BaseViewModel
import com.carefer.fixtures.domain.entity.local.MatchItem
import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.usecase.GetFavouredMatchesListUseCase
import com.carefer.fixtures.domain.usecase.GetMatchesListUseCase
import com.carefer.fixtures.domain.usecase.SetMatchFavourUseCase
import com.carefer.fixtures.domain.usecase.SetMatchUnFavourUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FixturesViewModel @Inject constructor(
    private val getMatchesListUseCase: GetMatchesListUseCase,
    private val getFavouredMatchesListUseCase: GetFavouredMatchesListUseCase,
    private val setMatchFavourUseCase: SetMatchFavourUseCase,
    private val setMatchUnFavourUseCase: SetMatchUnFavourUseCase
) : BaseViewModel() {

    private val fixturesListIntent = MutableLiveData<FixturesIntent>()

    private val fixturesListObserver = Observer<FixturesIntent> {
        when (it) {

            is FixturesIntent.GetFixturesList -> {
                getFixturesList(it.id)
            }

            is FixturesIntent.GetFavouredMatchesList -> {
                getFavouredMatches(Unit)
            }

            is FixturesIntent.SetMatchFavour -> {
                setMatchFavour(it.id)
            }

            is FixturesIntent.RemoveMatchFavour -> {
                setMatchUnFavour(it.id)
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
            getMatchesListUseCase.executeApiState(
                FixturesListQuery(id),
                it
            )
        }
    }

    private val _getFavouredMatchesListStateFlow =
        MutableStateFlow<List<String>>(listOf())
    val getFavouredMatchesListStateFlow: StateFlow<List<String>>
        get() = _getFavouredMatchesListStateFlow

    private fun getFavouredMatches(unit: Unit) {
        callLocalApi(_getFavouredMatchesListStateFlow) {
            viewModelScope.launch {
                getFavouredMatchesListUseCase.execute(
                    unit,
                    it
                )
            }
        }
    }


    private val _setMatchFavourStateFlow =
        MutableStateFlow(Unit)
    val setMatchFavourStateFlow: StateFlow<Unit>
        get() = _setMatchFavourStateFlow


    private fun setMatchFavour(id: String) {
        callLocalApi(_setMatchFavourStateFlow) {
            viewModelScope.launch {
                setMatchFavourUseCase.execute(
                    id,
                    it
                )
            }
        }
    }


    private val _setMatchUnFavourStateFlow =
        MutableStateFlow<Unit>(Unit)
    val setMatchUnFavourStateFlow: StateFlow<Unit>
        get() = _setMatchUnFavourStateFlow


    private fun setMatchUnFavour(id: String) {
        callLocalApi(_setMatchUnFavourStateFlow) {
            viewModelScope.launch {
                setMatchUnFavourUseCase.execute(
                    id,
                    it
                )
            }
        }
    }


    override fun reset() {
        super.reset()
        fixturesListIntent.removeObserver(fixturesListObserver)
    }
}