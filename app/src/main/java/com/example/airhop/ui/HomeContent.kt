package com.example.airhop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.airhop.R
import com.example.airhop.data.model.Airport
import com.example.airhop.data.model.Favorite
import com.example.airhop.data.model.Flight
import com.example.airhop.ui.components.FlightListItem
import com.example.airhop.ui.theme.AirHopTheme

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteFlights() {
    AirHopTheme {
        val favorites = List(9) {
            Favorite(
                id = it,
                departureCode = "YYZ",
                destinationCode = "AMS"
            )
        }

        FavoriteFlights(
            modifier = Modifier.fillMaxSize(),
            favorites = favorites,
            onFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFlightList() {
    AirHopTheme {

        val flights = List(9) {
            Flight(
                departureAirport = Airport(id = it),
                destinationAirport = Airport(id = it)
            )
        }

        FlightList(
            modifier = Modifier.fillMaxSize(),
            airportName = "YYZ",
            flights = flights,
            onFavoriteClick = {}
        )
    }
}

@Composable
fun FlightList(
    modifier: Modifier = Modifier,
    airportName: String,
    flights: List<Flight>,
    onFavoriteClick: (Flight) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(
                start = dimensionResource(R.dimen.main_container_padding),
                bottom = dimensionResource(R.dimen.main_container_title_text_padding_bottom)
            ),
            text = stringResource(R.string.flights_from_text, airportName),
            style = MaterialTheme.typography.titleSmall
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.list_item_vertical_arrangement_space)
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(
                horizontal = dimensionResource(R.dimen.main_container_padding),
                vertical = dimensionResource(R.dimen.main_container_padding)
            )
        ) {
            items(flights, key = { flight -> flight.destinationAirport.id }) { flight ->
                FlightListItem(
                    modifier = Modifier.fillMaxWidth(),
                    departureCode = flight.departureAirport.code,
                    departureName = flight.departureAirport.name,
                    destinationCode = flight.destinationAirport.code,
                    destinationName = flight.destinationAirport.name,
                    imageIcon = Icons.TwoTone.Star,
                    imageIconClick = { onFavoriteClick(flight) }
                )
            }
        }

    }
}

@Composable
fun FavoriteFlights(
    modifier: Modifier = Modifier,
    favorites: List<Favorite>,
    onFavoriteClick: (Favorite) -> Unit
) {
    Column(modifier = modifier) {
        if (favorites.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.main_container_padding),
                    bottom = dimensionResource(R.dimen.main_container_title_text_padding_bottom)
                ),
                text = stringResource(R.string.favorites_route_text),
                style = MaterialTheme.typography.titleSmall
            )
            FavoriteFlightList(
                modifier = Modifier.fillMaxWidth(),
                favorites = favorites,
                onFavoriteClick = onFavoriteClick,
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(R.dimen.main_container_padding),
                    vertical = dimensionResource(R.dimen.main_container_padding)
                )
            )
        } else {
            Text(
                text = stringResource(R.string.favorites_empty_text),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(dimensionResource(R.dimen.main_container_padding))
            )
        }
    }
}

@Composable
fun FavoriteFlightList(
    modifier: Modifier = Modifier,
    favorites: List<Favorite>,
    contentPadding: PaddingValues,
    onFavoriteClick: (Favorite) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_vertical_arrangement_space)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(favorites, key = { favorite -> favorite.id }) { favorite ->
            FlightListItem(
                modifier = Modifier.fillMaxWidth(),
                departureCode = favorite.departureCode,
                destinationCode = favorite.destinationCode,
                imageIconClick = { onFavoriteClick(favorite) },
                imageIcon = Icons.Default.Star,
                departureName = "",
                destinationName = ""
            )
        }
    }
}