package com.carefer.matches.domain.usecase


import com.carefer.core.data.mapper.CloudErrorMapper
import com.carefer.core.domain.usecase.base.RemoteUseCase
import com.carefer.matches.data.repository.MatchesRepository
import com.carefer.matches.domain.entity.local.MatchItem
import com.carefer.matches.domain.entity.query.MatchesListQuery
import com.carefer.matches.domain.entity.response.MatchesResponse
import com.carefer.matches.domain.mapper.MatchMapper
import javax.inject.Inject

class GetMatchesListUseCase @Inject constructor(
    errorUtil: CloudErrorMapper,
    private val matchesRepository: MatchesRepository,
    private val mapper: MatchMapper
) : RemoteUseCase<MatchesListQuery, MatchesResponse, List<MatchItem>>(
    errorUtil
) {
    public override suspend fun executeOnBackground(parameters: MatchesListQuery): MatchesResponse {
        return matchesRepository.getMatchesList(parameters)
    }

    public override suspend fun convert(dto: MatchesResponse): List<MatchItem> {
        val mappedList = dto.matches?.map {
            mapper.convert(it)
        } ?: listOf()
        return mappedList
    }
}