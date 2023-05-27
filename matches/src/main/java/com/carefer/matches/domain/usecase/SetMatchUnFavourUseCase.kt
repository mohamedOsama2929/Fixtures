package com.carefer.matches.domain.usecase


import com.carefer.core.domain.usecase.base.LocalUseCase
import com.carefer.matches.data.repository.MatchesRepository
import javax.inject.Inject

class SetMatchUnFavourUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
) : LocalUseCase<String, Unit>() {
    public override suspend fun executeOnBackground(parameters: String) {
        return matchesRepository.removeMatchFavour(parameters)
    }
}