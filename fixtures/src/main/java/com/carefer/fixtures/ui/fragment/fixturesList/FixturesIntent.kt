package com.carefer.fixtures.ui.fragment.fixturesList

sealed class FixturesIntent {
    class GetFixturesList(val id: String) : FixturesIntent()
    object GetFavouredMatchesList : FixturesIntent()
    class SetMatchFavour(val id: String) : FixturesIntent()
    class RemoveMatchFavour(val id: String) : FixturesIntent()
}