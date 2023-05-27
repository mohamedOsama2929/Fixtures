package com.carefer.matches.domain.usecase

import com.carefer.matches.data.repository.MatchesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class SetMatchUnFavourUseCaseTest {

    @Test
    fun `executeOnBackground should call removeMatchFavour with correct parameter`() = runBlocking {
        // Arrange
        val matchesRepository = mockk<MatchesRepository>()
        val useCase = SetMatchUnFavourUseCase(matchesRepository)
        val matchId = "123"

        coEvery { matchesRepository.removeMatchFavour(matchId) } returns Unit

        // Act
        useCase.executeOnBackground(matchId)

        // Assert
        coVerify { matchesRepository.removeMatchFavour(matchId) }
    }

    @Test
    fun `executeOnBackground should not throw any exception`() = runBlocking {
        // Arrange
        val matchesRepository = mockk<MatchesRepository>()
        val useCase = SetMatchUnFavourUseCase(matchesRepository)
        val matchId = "123"

        coEvery { matchesRepository.removeMatchFavour(matchId) } returns Unit

        // Act & Assert
        try {
            runBlocking { useCase.executeOnBackground(matchId) }
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
            val useCase = SetMatchUnFavourUseCase(matchesRepository)
            val matchId = "123"
            val errorMessage = "Failed to remove match favour"

            coEvery { matchesRepository.removeMatchFavour(matchId) } throws RuntimeException(
                errorMessage
            )

            // Act & Assert
            val exception = assertThrows(RuntimeException::class.java) {
                runBlocking { useCase.executeOnBackground(matchId) }
            }

            assertEquals(errorMessage, exception.message)
        }

}