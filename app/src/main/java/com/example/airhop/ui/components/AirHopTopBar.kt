package com.example.airhop.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.airhop.R
import com.example.airhop.ui.theme.AirHopTheme

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    AirHopTheme {
        AirHopTopBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirHopTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.top_bar_text),
                color = MaterialTheme.colorScheme.background
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )

    )
}