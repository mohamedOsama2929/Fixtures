package com.carefer.matches.ui.fragment.matchesList

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import com.carefer.matches.databinding.FragmentMatchesBinding
import com.carefer.matches.domain.entity.local.MatchItem
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

internal class MatchesFragmentHelperTest {

    @Mock
    lateinit var matchesAdapter: MatchesAdapter

    @Test
    fun `setMatchesFavourite should update the adapter list with correct favorites`() {
        // Arrange
        val matchesList = listOf(
            MatchItem(id = 1, isFavorite = false),
            MatchItem(id = 2, isFavorite = false),
            MatchItem(id = 3, isFavorite = false)
        )
        val favMatches = listOf("1", "3")
        val expectedList = listOf(
            MatchItem(id = 1, isFavorite = true),
            MatchItem(id = 2, isFavorite = false),
            MatchItem(id = 3, isFavorite = true)
        )


        // Act
        this.setMatchesFavourite(matchesAdapter, matchesList, favMatches)

        // Assert
        assertEquals(expectedList, matchesList)
        verify(matchesAdapter).submitList(expectedList)
    }

    @Test
    fun getDaysList() {
    }

    @Test
    fun handleShimmerLoading() {
        val helper = MatchesFragmentHelper()
        val binding = mockk<FragmentMatchesBinding>(relaxed = true)

// Act
        helper.handleShimmerLoading(true, true, binding)

// Assert
        assertTrue(binding.rvMatches.isVisible)
        assertTrue(binding.shimmerLayout.root.isVisible)
        verify { binding.shimmerLayout.root.startShimmerAnimation() }
    }


    @SuppressLint("CheckResult")
    @Test
    fun handleScrollToTodayItem() {

        // Arrange
        val helper = MatchesFragmentHelper()
        val binding = mockk<FragmentMatchesBinding>(relaxed = true)
        val matchesList = listOf(
            MatchItem(1),
            MatchItem(2, isFavorite = false),
            MatchItem(3, isFavorite = false),
            MatchItem(4, isFavorite = true),
            MatchItem(5, isFavorite = false)
        )
// Act
        helper.handleScrollToTodayItem(binding, matchesList)

// Assert
        verify { binding.rvMatches.scrollToPosition(3) }
    }


    @Test
    fun setMatchesFavourite(
        matchesAdapter: MatchesAdapter,
        matchesList: List<MatchItem>,
        favMatches: List<String>
    ) {

        // Arrange
        val helper = MatchesFragmentHelper()
        val matchesAdapter = MatchesAdapter(::onItemClicked)
        val matchesList = listOf(
            MatchItem(1, isFavorite = false),
            MatchItem(2, isFavorite = false),
            MatchItem(3, isFavorite = false)
        )
        val favMatches: List<String>? = null

// Act
        helper.setMatchesFavourite(matchesAdapter, matchesList, favMatches)

// Assert
        assertEquals(matchesList, matchesAdapter.currentList)
    }

    private fun onItemClicked(item: MatchItem) {

    }

    @Test
    fun getFavMatches() {

        // Arrange
        val helper = MatchesFragmentHelper()
        val matchesList = mutableListOf(
            MatchItem(1, isFavorite = false),
            MatchItem(2, isFavorite = true),
            MatchItem(3, isFavorite = true),
            MatchItem(4, isFavorite = false),
            MatchItem(5, isFavorite = true)
        )

// Act
        val favMatches = helper.getFavMatches(matchesList)

// Assert
        assertNotNull(favMatches)
        assertEquals(3, favMatches?.size)
        assertTrue(favMatches?.all { it.isFavorite } == true)
    }
}