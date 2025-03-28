package com.example.airhop.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.airhop.R
import com.example.airhop.ui.theme.AirHopTheme
import com.example.airhop.ui.theme.Shapes

@Preview
@Composable
fun PreviewFlightListItem() {
    AirHopTheme {
        FlightListItem(
            modifier = Modifier.fillMaxWidth(),
            departureCode = "YYZ",
            departureName = "Toronto Pearson Airport",
            destinationCode = "AMS",
            destinationName = "Amsterdam Airport Schiphol",
            imageIcon = Icons.Default.Star,
            imageIconClick = {},
        )
    }
}

@Composable
fun FlightListItem(
    modifier: Modifier = Modifier,
    departureCode: String,
    departureName: String,
    destinationCode: String,
    destinationName: String,
    imageIcon: ImageVector,
    imageIconClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = Shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
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
            IconButton(onClick = imageIconClick) {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.list_item_card_favorite_button_size)),
                    imageVector = imageIcon,
                    contentDescription = stringResource(R.string.favorite_icon),
                    tint = MaterialTheme.colorScheme.inverseSurface
                )
            }
        }
    }
}