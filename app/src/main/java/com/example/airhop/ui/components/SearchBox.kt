package com.example.airhop.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.airhop.R
import com.example.airhop.data.model.Airport
import com.example.airhop.ui.theme.Shapes

@Preview(showBackground = true, name = "Search Bar")
@Composable
fun PreviewSearchBar() {
    SearchBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        text = "",
        onTextValueChange = {},
        isExpanded = false,
        airports = List(9) {
            Airport(
                id = it,
                code = "YYZ",
                name = "Toronto Pearson",
                annualPassengerVisits = 50_000
            )
        },
        onExpandedValueChange = {},
        onSearchItemClick = {},
        onSearchActionClick = {},
        onTrailingIconAction = {}
    )
}

@Preview(showBackground = true, name = "Search Bar Expanded")
@Composable
fun PreviewSearchContainer() {
    SearchBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        text = "",
        onTextValueChange = {},
        isExpanded = true,
        airports = List(9) {
            Airport(
                id = it,
                code = "YYZ",
                name = "Toronto Pearson",
                annualPassengerVisits = 50_000
            )
        },
        onExpandedValueChange = {},
        onSearchItemClick = {},
        onSearchActionClick = {},
        onTrailingIconAction = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    text: String,
    onTextValueChange: (String) -> Unit,
    isExpanded: Boolean,
    onExpandedValueChange: (Boolean) -> Unit,
    onTrailingIconAction: () -> Unit,
    airports: List<Airport>,
    onSearchItemClick: (Airport) -> Unit,
    onSearchActionClick: (String) -> Unit
) {
    Card(
        shape = Shapes.medium,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            shape = SearchBarDefaults.inputFieldShape,
            inputField = {
                SearchBarDefaults.InputField(
                    modifier = Modifier.clip(Shapes.medium),
                    query = text,
                    onQueryChange = { onTextValueChange(it) },
                    onSearch = { onSearchActionClick(it) },
                    enabled = true,
                    placeholder = { Text(stringResource(R.string.search_airport_text)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon)
                        )
                    },
                    trailingIcon = {
                        if (isExpanded) {
                            IconButton(
                                onClick = { onTrailingIconAction() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(R.string.search_bar_close_icon)
                                )
                            }
                        }
                    },
                    expanded = isExpanded,
                    onExpandedChange = { onExpandedValueChange(it) }
                )
            },
            expanded = isExpanded,
            onExpandedChange = { onExpandedValueChange(it) },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            tonalElevation = dimensionResource(R.dimen.search_bar_tonal_elevation),
            windowInsets = WindowInsets(dimensionResource(R.dimen.search_bar_top_window_inset)),
            content = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        vertical = dimensionResource(R.dimen.search_bar_suggestion_container_vertical_padding),
                        horizontal = dimensionResource(R.dimen.search_bar_suggestion_container_horizontal_padding)
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.search_bar_suggestion_container_vertical_arrangement_space)
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(airports, key = { airport -> airport.id }) { airport ->
                        SuggestionListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = { onSearchItemClick(airport) },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ),
                            airportCode = airport.code,
                            airportName = airport.name
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun SuggestionListItem(
    modifier: Modifier = Modifier,
    airportCode: String,
    airportName: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.search_bar_suggestion_container_list_item_horizontal_arrangement_space)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = airportCode,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = airportName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}