package com.carefer.matches.domain.usecase

import com.carefer.matches.data.repository.MatchesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class SetMatchFavourUseCaseTest {
    @Test
    fun `executeOnBackground should call setMatchFavour with correct parameter`() = runBlocking {
        // Arrange
        val matchesRepository = mockk<MatchesRepository>()
        val useCase = SetMatchFavourUseCase(matchesRepository)
        val matchId = "123"

        coEvery { matchesRepository.setMatchFavour(matchId) } returns Unit

        // Act
        useCase.executeOnBackground(matchId)

        // Assert
        coVerify { matchesRepository.setMatchFavour(matchId) }
    }


    @Test
    fun `executeOnBackground should not throw any exception`() = runBlocking {
        // Arrange
        val matchesRepository = mockk<MatchesRepository>()
        val useCase = SetMatchFavourUseCase(matchesRepository)
        val matchId = "123"

        coEvery { matchesRepository.setMatchFavour(matchId) } returns Unit

        // Act & Assert
        try {
            useCase.executeOnBackground(matchId)
        } catch (e: Exception) {
            // Fail the test if an exception is thrown
            fail("An unexpected exception occurred: ${e.message}")
        }
    }

    @Test
    fun `executeOnBackground should throw an exception when matchesRepository throws an exception`() =
        runBlocking {
            // Arrange
            val matchesRepository = mockk<MatchesRepository>()
            val useCase = SetMatchFavourUseCase(matchesRepository)
            val matchId = "123"
            val errorMessage = "Failed to set match favour"

            coEvery { matchesRepository.setMatchFavour(matchId) } throws RuntimeException(
                errorMessage
            )

            // Act & Assert
            val exception = assertThrows(RuntimeException::class.java) {
                runBlocking {
                    useCase.executeOnBackground(matchId)
                }
            }

            Assert.assertEquals(errorMessage, exception.message)
        }
}