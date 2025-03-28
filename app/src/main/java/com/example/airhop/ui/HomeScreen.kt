package com.example.airhop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.airhop.AppViewModelProvider
import com.example.airhop.R
import com.example.airhop.ui.components.AirHopTopBar
import com.example.airhop.ui.components.SearchBox
import com.example.airhop.ui.theme.AirHopTheme

@Preview
@Composable
fun PreviewHomeScreen() {
    AirHopTheme {
    }
}

@Composable
fun AirHopApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AirHopTopBar() }
    ) { innerPadding ->

        val homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
        val favoriteUiState by homeViewModel.favoriteUiState.collectAsState()
        val airports by homeViewModel.airportList.collectAsState()
        val searchQuery by homeViewModel.searchQuery.collectAsState()

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.main_container_vertical_arrangement_space)
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBox(
                text = searchQuery,
                onTextValueChange = { homeViewModel.updateQueryString(it) },
                isExpanded = true,
                onExpandedValueChange = {},
                airports = airports,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.main_container_padding),
                        top = dimensionResource(R.dimen.main_container_padding),
                        end = dimensionResource(R.dimen.main_container_padding)
                    )
            )


            FavoriteContent(
                modifier = Modifier.fillMaxWidth(),
                favorites = favoriteUiState.favorites
            )
        }
    }
}