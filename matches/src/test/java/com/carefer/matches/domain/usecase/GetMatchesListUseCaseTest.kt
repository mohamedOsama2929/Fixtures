package com.carefer.matches.domain.usecase

import com.carefer.core.data.mapper.CloudErrorMapper
import com.carefer.matches.data.repository.MatchesRepository
import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.entity.response.MatchesResponse
import com.carefer.matches.domain.mapper.MatchMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetMatchesListUseCaseTest {

    @Test
    fun `executeOnBackground should return expected MatchesResponse`() = runBlocking {
        // Arrange
        val matchesListQuery = MatchesListQuery("2021")
        val expectedResponse = MatchesResponse()
        val matchesRepository = mockk<MatchesRepository>()
        val errorUtil = mockk<CloudErrorMapper>()
        val mapper = mockk<MatchMapper>()

        val useCase = GetMatchesListUseCase(errorUtil, matchesRepository, mapper)
        coEvery { matchesRepository.getMatchesList(matchesListQuery) } returns expectedResponse

        // Act
        val result = useCase.executeOnBackground(matchesListQuery)

        // Assert
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `convert should return expected List of MatchItem`() = runBlocking {
        // Arrange
        val dto = MatchesResponse(matches = listOf())
        val expectedMatchItems = listOf<MatchItem>()
        val matchesRepository = mockk<MatchesRepository>()
        val errorUtil = mockk<CloudErrorMapper>()
        val mapper = mockk<MatchMapper>()

        val useCase = GetMatchesListUseCase(errorUtil, matchesRepository, mapper)
        coEvery { mapper.convert(any()) } returnsMany expectedMatchItems

        // Act
        val result = useCase.convert(dto)

        // Assert
        assertEquals(expectedMatchItems, result)
    }

    @Test
    fun `convert should return empty List when MatchesResponse has no matches`() = runBlocking {
        // Arrange
        val dto = MatchesResponse(matches = null)
        val matchesRepository = mockk<MatchesRepository>()
        val errorUtil = mockk<CloudErrorMapper>()
        val mapper = mockk<MatchMapper>()

        val useCase = GetMatchesListUseCase(errorUtil, matchesRepository, mapper)

        // Act
        val result = useCase.convert(dto)

        // Assert
        assertEquals(emptyList<MatchItem>(), result)
    }
}