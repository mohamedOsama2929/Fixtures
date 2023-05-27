package com.carefer.matches.ui.fragment.matchesList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.carefer.core.base.view_model.BaseViewModel
import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.usecase.GetFavouredMatchesListUseCase
import com.carefer.matches.domain.usecase.GetMatchesListUseCase
import com.carefer.matches.domain.usecase.SetMatchFavourUseCase
import com.carefer.matches.domain.usecase.SetMatchUnFavourUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val getMatchesListUseCase: GetMatchesListUseCase,
    private val getFavouredMatchesListUseCase: GetFavouredMatchesListUseCase,
    private val setMatchFavourUseCase: SetMatchFavourUseCase,
    private val setMatchUnFavourUseCase: SetMatchUnFavourUseCase
) : BaseViewModel() {

    private val matchesListIntent = MutableLiveData<MatchesIntent>()

    private val matchesListObserver = Observer<MatchesIntent> {
        when (it) {

            is MatchesIntent.GetMatchesList -> {
                getMatchesList(it.id)
            }

            is MatchesIntent.GetFavouredMatchesList -> {
                getFavouredMatches(Unit)
            }

            is MatchesIntent.SetMatchFavour -> {
                setMatchFavour(it.id)
            }

            is MatchesIntent.RemoveMatchFavour -> {
                setMatchUnFavour(it.id)
            }
        }
    }

    internal fun send(intentType: MatchesIntent) {
        matchesListIntent.value = intentType
    }

    override fun handelViewIntent() {
        viewModelScope.launch {
            matchesListIntent.observeForever(matchesListObserver)
        }
    }

    fun setMatchesListStateFlow(matchesList: List<MatchItem>) {
        _getMatchesListStateFlow.value = matchesList
    }

    fun setFavourMatchesListStateFlow(matchesList: List<String>) {
        _getFavouredMatchesListStateFlow.value = matchesList
    }

    private val _getMatchesListStateFlow =
        MutableStateFlow<List<MatchItem>>(listOf())
    val getMatchesListStateFlow: StateFlow<List<MatchItem>>
        get() = _getMatchesListStateFlow

    fun getMatchesList(id: String) {
        callApi(_getMatchesListStateFlow, isShimmer = true) {
            getMatchesListUseCase.execute(
                MatchesListQuery(id),
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
        matchesListIntent.removeObserver(matchesListObserver)
    }
}