package com.carefer.fixtures.domain.usecase


import com.carefer.core.domain.usecase.base.LocalUseCase
import com.carefer.fixtures.data.repository.FixturesRepository
import javax.inject.Inject

class GetFavouredMatchesListUseCase @Inject constructor(
    private val fixturesRepository: FixturesRepository,
) : LocalUseCase<Unit, List<String>>() {
    override suspend fun executeOnBackground(parameters: Unit): List<String> {
        return fixturesRepository.getFavouredMatchesList()
    }
}