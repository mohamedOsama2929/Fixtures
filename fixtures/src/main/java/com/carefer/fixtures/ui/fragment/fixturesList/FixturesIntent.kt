package com.carefer.fixtures.ui.fragment.fixturesList

sealed class FixturesIntent {
    class GetFixturesList(val id: String) : FixturesIntent()
}