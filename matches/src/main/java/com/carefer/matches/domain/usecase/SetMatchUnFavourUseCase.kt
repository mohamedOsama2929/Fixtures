package com.carefer.matches.domain.usecase


import com.carefer.core.domain.usecase.base.LocalUseCase
import com.carefer.matches.data.repository.MatchesRepository
import javax.inject.Inject

class SetMatchUnFavourUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
) : LocalUseCase<String, Unit>() {
    override suspend fun executeOnBackground(parameters: String): Unit {
        return matchesRepository.removeMatchFavour(parameters)
    }
}