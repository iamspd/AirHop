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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.airhop.R
import com.example.airhop.data.Favorite
import com.example.airhop.ui.components.FlightListItem
import com.example.airhop.ui.theme.AirHopTheme

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteContent() {
    AirHopTheme {
        val favorites = List(9) {
            Favorite(
                id = it,
                departureCode = "YYZ",
                //departureName = "Toronto Pearson",
                destinationCode = "AMS",
                //destinationName = "Amsterdam Airport Schiphol"
            )
        }

        FavoriteContent(
            modifier = Modifier.fillMaxSize(),
            favorites = favorites
        )
    }
}

@Composable
fun FavoriteContent(
    modifier: Modifier = Modifier,
    favorites: List<Favorite>
) {
    Column(
        modifier = modifier
    ) {
        if (favorites.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.main_container_padding)
                ),
                text = stringResource(R.string.favorites_route_text),
                style = MaterialTheme.typography.titleSmall
            )
            FavoriteFlightList(
                modifier = Modifier.fillMaxWidth(),
                favorites = favorites,
                paddingValues = PaddingValues(
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
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_vertical_arrangement_space)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(favorites, key = { favorite -> favorite.id }) { favorite ->
            FlightListItem(
                modifier = Modifier.fillMaxWidth(),
                departureCode = favorite.departureCode,
                destinationCode = favorite.destinationCode,
                imageIconClick = {},
                imageIcon = Icons.Default.Star,
                departureName = "",
                destinationName = ""
            )
        }
    }
}