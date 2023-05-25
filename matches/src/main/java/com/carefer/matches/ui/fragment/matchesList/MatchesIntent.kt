package com.carefer.matches.ui.fragment.matchesList

sealed class MatchesIntent {
    class GetMatchesList(val id: String) : MatchesIntent()
    object GetFavouredMatchesList : MatchesIntent()
    class SetMatchFavour(val id: String) : MatchesIntent()
    class RemoveMatchFavour(val id: String) : MatchesIntent()
}