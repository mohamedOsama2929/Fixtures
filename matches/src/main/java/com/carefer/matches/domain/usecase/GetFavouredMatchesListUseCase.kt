package com.carefer.matches.domain.usecase


import com.carefer.core.domain.usecase.base.LocalUseCase
import com.carefer.matches.data.repository.MatchesRepository
import javax.inject.Inject

class GetFavouredMatchesListUseCase @Inject constructor(
    private val matchesRepository: MatchesRepository,
) : LocalUseCase<Unit, List<String>>() {
    override suspend fun executeOnBackground(parameters: Unit): List<String> {
        return matchesRepository.getFavouredMatchesList()
    }
}