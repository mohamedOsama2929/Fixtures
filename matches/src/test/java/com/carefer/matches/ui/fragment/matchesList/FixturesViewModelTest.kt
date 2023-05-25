package com.carefer.matches.ui.fragment.matchesList

import androidx.lifecycle.MutableLiveData
import com.carefer.domain.usecase.announcements.AnnouncementsFavouriteListUsecase
import com.carefer.domain.usecase.announcements.AnnouncementsListUsecase
import com.carefer.matches.domain.entity.local.Announcement
import com.carefer.matches.utils.InstantExecutorExtension
import com.carefer.matches.utils.LiveDataTestUtil
import com.carefer.matches.utils.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class FixturesViewModelTest {

    //system under test
    private lateinit var matchesViewModel: MatchesViewModel

    private lateinit var announcementListUseCase: AnnouncementsListUsecase
    private lateinit var announcementsFavouriteListUsecase: AnnouncementsFavouriteListUsecase

    @BeforeEach
    fun init() {
        announcementListUseCase = Mockito.mock(AnnouncementsListUsecase::class.java)
        announcementsFavouriteListUsecase =
            Mockito.mock(AnnouncementsFavouriteListUsecase::class.java)
        matchesViewModel =
            MatchesViewModel(announcementListUseCase, announcementsFavouriteListUsecase)
    }


    @Test
    fun retrieveAnnouncementListFromLiveData_returnAnnouncementList() = runBlocking {
        //Arrange
        val returnedData = ResponsePagingResultModel<Announcement>(
            TestUtils.ANNOUNCEMENTS_LIST_BO,
            2,
            1
        )

        val liveDataTestUtils = LiveDataTestUtil<ResponsePagingResultModel<Announcement>>()
        val returnedValue: MutableLiveData<ResponsePagingResultModel<Announcement>> =
            MutableLiveData<ResponsePagingResultModel<Announcement>>()
        returnedValue.value = returnedData

        matchesViewModel.setAnnouncementLiveData(returnedData)

        //Act
        matchesViewModel.getAnnouncementsList(1)
        val observedData: ResponsePagingResultModel<Announcement> =
            liveDataTestUtils.getValue(matchesViewModel.announcementsListLiveData)

        //Assert
        Assertions.assertEquals(returnedData, observedData)
    }


    @Test
    fun retrieveFavoriteAnnouncementListFromLiveData_returnFavoriteAnnouncementList() =
        runBlocking {
            //Arrange
            val returnedData = ResponsePagingResultModel<Announcement>(
                TestUtils.ANNOUNCEMENTS_LIST_BO,
                2,
                1
            )

            val liveDataTestUtils = LiveDataTestUtil<ResponsePagingResultModel<Announcement>>()
            val returnedValue: MutableLiveData<ResponsePagingResultModel<Announcement>> =
                MutableLiveData<ResponsePagingResultModel<Announcement>>()
            returnedValue.value = returnedData

            matchesViewModel.setFavoriteAnnouncementLiveData(returnedData)

            //Act
            matchesViewModel.getFavouriteAnnouncementsList(1)
            val observedData: ResponsePagingResultModel<Announcement> =
                liveDataTestUtils.getValue(matchesViewModel.favouriteAnnouncementsListLiveData)

            //Assert
            Assertions.assertEquals(returnedData, observedData)
        }

}
























