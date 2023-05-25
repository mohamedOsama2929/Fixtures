package com.carefer.matches.data.source.local

import com.carefer.core.data.local.StorageManager
import javax.inject.Inject


class MatchesLocalSourceImpl @Inject constructor(private val storageManager: StorageManager) :
    MatchesLocalSource {
    override suspend fun getFavouredMatches(): List<String> {
        return storageManager.favMatches
    }

    override suspend fun settMatchFavour(id: String) {
        val matchesList = storageManager.favMatches
        matchesList.add(id)
        storageManager.favMatches = matchesList

    }

    override suspend fun setMatchUnFavour(id: String) {
        val matchesList = storageManager.favMatches
        matchesList.remove(id)
        storageManager.favMatches = matchesList
    }

}



