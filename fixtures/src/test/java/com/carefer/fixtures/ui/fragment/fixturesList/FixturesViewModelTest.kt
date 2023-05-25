package com.carefer.fixtures.ui.fragment.fixturesList

import androidx.lifecycle.MutableLiveData
import com.carefer.domain.usecase.announcements.AnnouncementsFavouriteListUsecase
import com.carefer.domain.usecase.announcements.AnnouncementsListUsecase
import com.carefer.fixtures.domain.entity.local.Announcement
import com.carefer.fixtures.utils.InstantExecutorExtension
import com.carefer.fixtures.utils.LiveDataTestUtil
import com.carefer.fixtures.utils.TestUtils
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
    private lateinit var fixturesViewModel: FixturesViewModel

    private lateinit var announcementListUseCase: AnnouncementsListUsecase
    private lateinit var announcementsFavouriteListUsecase: AnnouncementsFavouriteListUsecase

    @BeforeEach
    fun init() {
        announcementListUseCase = Mockito.mock(AnnouncementsListUsecase::class.java)
        announcementsFavouriteListUsecase =
            Mockito.mock(AnnouncementsFavouriteListUsecase::class.java)
        fixturesViewModel =
            FixturesViewModel(announcementListUseCase, announcementsFavouriteListUsecase)
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

        fixturesViewModel.setAnnouncementLiveData(returnedData)

        //Act
        fixturesViewModel.getAnnouncementsList(1)
        val observedData: ResponsePagingResultModel<Announcement> =
            liveDataTestUtils.getValue(fixturesViewModel.announcementsListLiveData)

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

            fixturesViewModel.setFavoriteAnnouncementLiveData(returnedData)

            //Act
            fixturesViewModel.getFavouriteAnnouncementsList(1)
            val observedData: ResponsePagingResultModel<Announcement> =
                liveDataTestUtils.getValue(fixturesViewModel.favouriteAnnouncementsListLiveData)

            //Assert
            Assertions.assertEquals(returnedData, observedData)
        }

}























