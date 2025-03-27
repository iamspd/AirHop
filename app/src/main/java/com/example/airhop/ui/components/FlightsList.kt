package com.example.airhop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.airhop.R
import com.example.airhop.data.Favorite
import com.example.airhop.ui.theme.AirHopTheme
import com.example.airhop.ui.theme.Shapes

@Preview(showBackground = true)
@Composable
fun PreviewFavoritesList() {
    AirHopTheme {
        val favorites = List(9) {
            Favorite(
                id = it,
                departureCode = "YYZ",
                departureName = "Toronto Pearson",
                destinationCode = "YYZ",
                destinationName = "Toronto Pearson"
            )
        }

        FavoritesList(
            modifier = Modifier.fillMaxSize(),
            favorites = favorites,
            paddingValues = PaddingValues(16.dp),
            isFavorite = false
        )
    }
}

@Composable
fun FavoritesList(
    modifier: Modifier = Modifier,
    favorites: List<Favorite>,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    isFavorite: Boolean
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_vertical_arrangement_space)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(favorites, key = { favorite -> favorite.id }) { favorite ->
            FlightCardListItem(
                modifier = Modifier.fillMaxWidth(),
                departureCode = favorite.departureCode,
                departureName = favorite.departureName,
                destinationCode = favorite.destinationCode,
                destinationName = favorite.destinationName,
                isFavorite = isFavorite
            )
        }
    }
}

@Composable
fun FlightCardListItem(
    modifier: Modifier = Modifier,
    departureCode: String,
    departureName: String,
    destinationCode: String,
    destinationName: String,
    isFavorite: Boolean
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ), shape = Shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(R.dimen.list_item_card_vertical_padding),
                    horizontal = dimensionResource(R.dimen.list_item_card_horizontal_padding)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen.list_item_card_text_vertical_arrangement_space)
                )
            ) {
                Text(
                    text = stringResource(R.string.depart).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_horizontal_arrangement_space)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = departureCode,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = departureName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Text(
                    text = stringResource(R.string.arrive).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.list_item_horizontal_arrangement_space)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = destinationCode,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = destinationName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.list_item_card_favorite_button_size)),
                    imageVector = if (isFavorite) {
                        Icons.Default.Star
                    } else {
                        Icons.TwoTone.Star
                    },
                    contentDescription = stringResource(R.string.favorite_icon),
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            }
        }
    }
}