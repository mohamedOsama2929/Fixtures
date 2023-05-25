package com.carefer.fixtures.domain.usecase


import com.carefer.core.domain.usecase.base.LocalUseCase
import com.carefer.fixtures.data.repository.FixturesRepository
import javax.inject.Inject

class SetMatchUnFavourUseCase @Inject constructor(
    private val fixturesRepository: FixturesRepository,
) : LocalUseCase<String, Unit>() {
    override suspend fun executeOnBackground(parameters: String): Unit {
        return fixturesRepository.removeMatchFavour(parameters)
    }
}