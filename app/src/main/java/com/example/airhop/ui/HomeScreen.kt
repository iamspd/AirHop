package com.example.airhop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.airhop.AppViewModelProvider
import com.example.airhop.R
import com.example.airhop.ui.components.AirHopTopBar
import com.example.airhop.ui.components.SearchBox
import com.example.airhop.ui.viewmodel.FavoriteViewModel
import com.example.airhop.ui.viewmodel.HomeViewModel

@Composable
fun AirHopApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AirHopTopBar() }
    ) { innerPadding ->

        val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val airports by homeViewModel.airportList.collectAsState()

        val searchBarState by homeViewModel.searchBarState.collectAsState()
        val flights by homeViewModel.searchFlights().collectAsState(initial = emptyList())
        val isFavoriteFlightShowing by homeViewModel.isFavoriteFlightsShowing.collectAsState()

        val searchQuery by homeViewModel.searchQuery.collectAsState()

        /**
         * Adding this variable as for searchQuery, it is saving the typed query into the
         * Data Store and when assigned to display updated Query on the search bar input field,
         * the input field was recomposing itself too many times, skipping input alphabets from the
         * keyboard. Hence, this solution.
         */
        var localQuery by rememberSaveable { mutableStateOf("") }

        val favoriteViewModel: FavoriteViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val favoriteUiState by favoriteViewModel.favoriteUiState.collectAsState()
        /**
         * Set the saved value of searchQuery to localQuery if Preference has it.
         */
        LaunchedEffect(searchQuery) {
            if (localQuery.isBlank()) {
                localQuery = searchQuery
            }
        }

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.main_container_vertical_arrangement_space)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBox(
                text = localQuery,
                onTextValueChange = {
                    localQuery = it
                    homeViewModel.updateQueryString(it)
                },
                isExpanded = searchBarState,
                onExpandedValueChange = homeViewModel::updateSearchBarState,
                onTrailingIconAction = {
                    if (localQuery.isBlank()) {
                        homeViewModel.updateSearchBarState(isExpanded = false)
                    } else {
                        localQuery = ""
                        homeViewModel.updateQueryString(query = "")
                    }
                },
                airports = airports,
                onSearchItemClick = {
                    localQuery = it.code
                    homeViewModel.updateQueryString(query = it.code)
                    homeViewModel.getDepartureAirport(airport = it)
                    homeViewModel.updateSearchBarState(isExpanded = false)
                    homeViewModel.updateHomeScreenContent(state = false)
                },
                onSearchActionClick = {
                    localQuery = airports.first().code
                    homeViewModel.updateQueryString(query = airports.first().code)
                    homeViewModel.getDepartureAirport(airport = airports.first())
                    homeViewModel.updateSearchBarState(isExpanded = false)
                    homeViewModel.updateHomeScreenContent(state = false)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.main_container_padding),
                        top = dimensionResource(R.dimen.main_container_padding),
                        end = dimensionResource(R.dimen.main_container_padding)
                    )
            )

            if (localQuery.isBlank()) {
                homeViewModel.updateHomeScreenContent(state = true)
            }

            if (isFavoriteFlightShowing.not() && localQuery.isNotBlank()) {
                FlightList(
                    modifier = Modifier.fillMaxWidth(),
                    airportName = localQuery,
                    flights = flights,
                    onFavoriteClick = favoriteViewModel::addToFavorite
                )
            } else {
                FavoriteFlights(
                    modifier = Modifier.fillMaxWidth(),
                    favorites = favoriteUiState.favorites,
                    onFavoriteClick = favoriteViewModel::removeFavorite
                )
            }
        }
    }
}