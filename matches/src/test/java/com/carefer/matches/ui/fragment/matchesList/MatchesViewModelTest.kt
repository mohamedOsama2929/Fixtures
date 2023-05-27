package com.carefer.matches.ui.fragment.matchesList

import androidx.lifecycle.asLiveData
import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.usecase.GetFavouredMatchesListUseCase
import com.carefer.matches.domain.usecase.GetMatchesListUseCase
import com.carefer.matches.domain.usecase.SetMatchFavourUseCase
import com.carefer.matches.domain.usecase.SetMatchUnFavourUseCase
import com.carefer.matches.utils.InstantExecutorExtension
import com.carefer.matches.utils.LiveDataTestUtil
import com.carefer.matches.utils.TestUtils
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MatchesViewModelTest {

    //system under test
    private lateinit var matchesViewModel: MatchesViewModel

    private lateinit var getMatchesListUseCase: GetMatchesListUseCase
    private lateinit var getFavouredMatchesListUseCase: GetFavouredMatchesListUseCase
    private lateinit var setMatchFavourUseCase: SetMatchFavourUseCase
    private lateinit var setMatchUnFavourUseCase: SetMatchUnFavourUseCase


    @BeforeEach
    fun init() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        getMatchesListUseCase = mockk()
        getFavouredMatchesListUseCase = mockk()
        setMatchFavourUseCase = mockk()
        setMatchUnFavourUseCase = mockk()
        matchesViewModel =
            MatchesViewModel(
                getMatchesListUseCase,
                getFavouredMatchesListUseCase,
                setMatchFavourUseCase,
                setMatchUnFavourUseCase
            )
    }


    @Test
    fun retrieveMatchesListFromLiveData_returnMatchesList() = runBlocking {
        //Arrange
        val returnedData = TestUtils.MATCHES_LIST

        val liveDataTestUtils = LiveDataTestUtil<List<MatchItem>>()
        val returnedValue: MutableStateFlow<List<MatchItem>> =
            MutableStateFlow(listOf())
        returnedValue.value = returnedData

        matchesViewModel.setMatchesListStateFlow(returnedData)

        //Act
        matchesViewModel.send(MatchesIntent.GetMatchesList("2021"))
        val observedData: List<MatchItem> =
            liveDataTestUtils.getValue(matchesViewModel.getMatchesListStateFlow.asLiveData())

        //Assert
        Assertions.assertEquals(returnedData, observedData)
    }


    @Test
    fun retrieveFavourMatchesListFromLiveData_returnFavourMatchesList() = runBlocking {
        //Arrange
        val returnedData = TestUtils.FAFOUR_MATCHES_LIST

        val liveDataTestUtils = LiveDataTestUtil<List<String>>()
        val returnedValue: MutableStateFlow<List<String>> =
            MutableStateFlow(listOf())
        returnedValue.value = returnedData

        matchesViewModel.setFavourMatchesListStateFlow(returnedData)

        //Act
        matchesViewModel.send(MatchesIntent.GetFavouredMatchesList)
        val observedData: List<String> =
            liveDataTestUtils.getValue(matchesViewModel.getFavouredMatchesListStateFlow.asLiveData())

        //Assert
        Assertions.assertEquals(returnedData, observedData)
    }


    @AfterEach
    fun cleanup() {
        Dispatchers.resetMain()
    }

}
























