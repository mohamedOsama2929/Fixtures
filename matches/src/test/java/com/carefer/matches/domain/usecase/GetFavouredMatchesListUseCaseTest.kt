package com.carefer.matches.domain.usecase

import com.carefer.matches.data.repository.MatchesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class GetFavouredMatchesListUseCaseTest {


    @Test
    fun `executeOnBackground should return expected List Of String(ids)`() = runBlocking {
        // Arrange
        val expectedResponse: List<String> = listOf("1", "2", "3")
        val matchesRepository = mockk<MatchesRepository>()

        val useCase = GetFavouredMatchesListUseCase(matchesRepository)
        coEvery { matchesRepository.getFavouredMatchesList() } returns expectedResponse

        // Act
        val result = useCase.executeOnBackground(Unit)

        // Assert
        Assertions.assertEquals(expectedResponse, result)
    }


    @Test
    fun `executeOnBackground should return an empty list when no favoured matches`() = runBlocking {
        // Arrange
        val matchesRepository = mockk<MatchesRepository>()
        val useCase = GetFavouredMatchesListUseCase(matchesRepository)
        val expectedList = emptyList<String>()

        coEvery { matchesRepository.getFavouredMatchesList() } returns expectedList

        // Act
        val result = useCase.executeOnBackground(Unit)

        // Assert
        Assertions.assertEquals(expectedList, result)
    }


}


