package com.carefer.fixtures.domain.usecase


import com.carefer.core.data.mapper.CloudErrorMapper
import com.carefer.core.domain.usecase.base.RemoteUseCase
import com.carefer.fixtures.data.repository.FixturesRepository
import com.carefer.fixtures.domain.entity.local.MatchItem
import com.carefer.fixtures.domain.entity.query.FixturesListQuery
import com.carefer.fixtures.domain.entity.response.FixturesResponse
import com.carefer.fixtures.domain.mapper.FixtureMapper
import javax.inject.Inject

class GetMatchesListUseCase @Inject constructor(
    errorUtil: CloudErrorMapper,
    private val fixturesRepository: FixturesRepository,
    private val mapper: FixtureMapper
) : RemoteUseCase<FixturesListQuery, FixturesResponse, List<MatchItem>>(
    errorUtil
) {
    public override suspend fun executeOnBackground(parameters: FixturesListQuery): FixturesResponse {
        return fixturesRepository.getMatchesList(parameters)
    }

    public override suspend fun convert(dto: FixturesResponse): List<MatchItem> {
        val mappedList = dto.matches?.map {
            mapper.convert(it)
        } ?: listOf()
        return mappedList
    }
}